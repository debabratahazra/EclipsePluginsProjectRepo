package com.zealcore.se.iw.wizard.internal;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zealcore.se.iw.FieldDescriptor;
import com.zealcore.se.iw.GenericTextImportData;

public final class TextParser {

    private static final String LINEBREAK_REGEXP = "[\\r\\n]{1,}";

    private static final String CHARSET = "ISO-8859-1";

    private static final String START_OF_STRING = "^";

    private static final String EMPTY_LINE_PATTERN = "\\s*";

    private boolean descriptorsAreStillMatching;

    private int currentPosition = 0;

    public TextParser() {}

    public void parse(final File file, final GenericTextImportData config,
            AbstractTextTreeVisitor client) throws IOException {
        this.descriptorsAreStillMatching = true;
        StringBuilder sb = new StringBuilder();
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        try {
            for (int i = 0; i < config.getNoOfHeaderLines(); i++) {
                String line = readLine(raf);
                sb.append(line);
            }

            /*
             * if (sb.length() > 0) { final Header header = new Header(0,
             * sb.toString()); if (client != null) { client.visitHeader(header);
             * } currentPosition = header.length(); } else { currentPosition =
             * 0; }
             */
            String currLine;
            List<Message> messages = new ArrayList<Message>();
            while ((currLine = readLine(raf)) != null) {
                boolean skipped = skipLine(currLine, config);
                if (!skipped) {
                    readMessage(currLine, config, messages);

                    if (messages == null || messages.size() < 1) {
                        break;
                    }
                    if (client != null) {
                        for (Message message2 : messages) {
                            client.visitMessage(message2);
                            for (final AbstractTextNode node : message2
                                    .getChildren()) {
                                client.visitField((Field) node);
                            }
                            client.visitMessageEnd();
                        }
                    }
                    messages.clear();
                } else {
                    this.currentPosition = this.currentPosition
                            + currLine.length();
                }
            }
        } finally {
            raf.close();
        }
    }

    public MessageTree parse(final File file, final GenericTextImportData config)
            throws IOException {
        this.descriptorsAreStillMatching = true;
        final MessageTree tree = new MessageTree();

        StringBuilder sb = new StringBuilder();
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        try {
            for (int i = 0; i < config.getNoOfHeaderLines(); i++) {
                String line = readLine(raf);
                sb.append(line);
            }

            if (sb.length() > 0) {
                final Header header = new Header(0, sb.toString());
                tree.add(header);
                currentPosition = header.length();
            } else {
                currentPosition = 0;
            }
            String currLine;
            List<Message> messages = new ArrayList<Message>();
            while ((currLine = readLine(raf)) != null) {
                boolean skipped = skipLine(currLine, config);
                if (!skipped) {
                    readMessage(currLine, config, messages);

                    if (messages == null || messages.size() < 1) {
                        break;
                    }
                    for (Message message2 : messages) {
                        tree.add(message2);
                    }
                    messages.clear();
                } else {
                    this.currentPosition = this.currentPosition
                            + currLine.length();
                }
            }
        } finally {
            raf.close();
        }
        return tree;
    }

    public MessageTree parse(final String text,
            final GenericTextImportData config) {
        this.descriptorsAreStillMatching = true;
        final MessageTree tree = new MessageTree();

        StringBuilder sb = new StringBuilder();

        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(
                text.getBytes()));
        for (int i = 0; i < config.getNoOfHeaderLines(); i++) {
            String line = readLine(dis);
            sb.append(line);
        }

        if (sb.length() > 0) {
            final Header header = new Header(0, sb.toString());
            tree.add(header);
            currentPosition = header.length();
        } else {
            currentPosition = 0;
        }
        String currLine;
        List<Message> messages = new ArrayList<Message>();
        while ((currLine = readLine(dis)) != null) {
            boolean skipped = skipLine(currLine, config);
            if (!skipped) {
                readMessage(currLine, config, messages);

                if (messages == null || messages.size() < 1) {
                    break;
                }
                for (Message message2 : messages) {
                    tree.add(message2);
                }
                messages.clear();
            } else {
                this.currentPosition = this.currentPosition + currLine.length();
            }
        }
        return tree;
    }

    public String readLine(final DataInputStream dis) {
        StringBuilder sb = new StringBuilder();
        try {
            if (dis != null && dis.available() > 1) {
                boolean lineFeedFound = false;
                while (!lineFeedFound) {
                    byte byten = dis.readByte();
                    String c = convertByteToString(byten);
                    sb.append(c);
                    if (c.contains("\n")) {
                        lineFeedFound = true;
                    } else if (c.contains("\r")) {
                        lineFeedFound = true;
                        if (dis.markSupported()) {
                            dis.mark(2);
                            byten = dis.readByte();
                            c = convertByteToString(byten);
                            if (c.contains("\n")) {
                                sb.append(c);
                            } else {
                                dis.reset();
                            }
                        } else {
                            throw new RuntimeException(
                                    "Failed to parse text. DataInputStream.mark() is not supported. This might be an unsupported platform.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            // ignore exception and return bytes read
        }
        if (sb.length() < 1) {
            return null;
        }
        return sb.toString();
    }

    public String readLine(final RandomAccessFile file) {
        StringBuilder sb = new StringBuilder();
        if (file != null) {
            boolean lineFeedFound = false;
            try {
                while (!lineFeedFound) {
                    byte byten;
                    byten = file.readByte();
                    String c = convertByteToString(byten);
                    sb.append(c);
                    if (c.contains("\n")) {
                        lineFeedFound = true;
                    } else if (c.contains("\r")) {
                        lineFeedFound = true;
                        long filePointer = file.getFilePointer();
                        byten = file.readByte();
                        c = convertByteToString(byten);
                        if (c.contains("\n")) {
                            sb.append(c);
                        } else {
                            file.seek(filePointer);
                        }
                    }
                }
            } catch (IOException e) {
                // ignore exception and return bytes read
            }
        }
        if (sb.length() < 1) {
            return null;
        }
        return sb.toString();
    }

    private String convertByteToString(final byte byten) {
        byte[] bytearray = new byte[1];
        bytearray[0] = byten;
        try {
            return new String(bytearray, CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(
                    "Failed to convert byte '" + byten
                            + "' to string. Charset: " + CHARSET
                            + " is not supported.", e);
        }
    }

    private boolean skipLine(final String currLine,
            final GenericTextImportData config) {
        final Collection<String> filters = config.getFilters();
        if (config.getSkipEmptyLines()) {
            if (currLine.length() == 0) {
                return true;
            }
            Pattern p = Pattern.compile(TextParser.EMPTY_LINE_PATTERN);
            Matcher matcher = p.matcher(currLine);
            if (matcher.matches()) {
                return true;
            }
        }

        String line = currLine;
        int lastCR = currLine.lastIndexOf("\r");
        if (lastCR > 0) {
            line = currLine.substring(0, lastCR);
        } else {
            lastCR = currLine.lastIndexOf("\n");
            if (lastCR > 0) {
                line = currLine.substring(0, lastCR);
            }
        }
        for (final String filter : filters) {
            if (line.matches(filter)) {
                return true;
            }
        }

        return false;
    }

    private void readMessage(final String currLine,
            final GenericTextImportData config, final List<Message> messages) {
        if (config.getDescriptors().size() < 1) {
            return;
        }

        String oldDelimiter = config.getDescriptors()
                .get(config.getDescriptors().size() - 1).getDelimiter();
        Scanner scanner = new Scanner(currLine);
        while (scanner.hasNext()) {
            final Message message = new Message();
            int lineOffset = 0;
            for (final FieldDescriptor descriptor : config.getDescriptors()) {
                String delimiter = descriptor.getDelimiter();
                scanner.useDelimiter(delimiter);

                if (scanner.hasNext()) {
                    scanner.next();

                    final MatchResult result = scanner.match();
                    String data = result.group(0);
                    if (!data.matches(LINEBREAK_REGEXP)) {
                        if (!oldDelimiter
                                .startsWith(TextParser.START_OF_STRING)) {
                            data = data.replaceFirst(TextParser.START_OF_STRING
                                    + oldDelimiter, "");
                        } else {
                            data = data.replaceFirst(oldDelimiter, "");
                        }

                        if (!descriptor.getType().canMatch(data)) {
                            this.descriptorsAreStillMatching = false;
                        }

                        final int position = currentPosition + lineOffset;
                        oldDelimiter = scanner.delimiter().pattern();
                        final Field field = new Field(position, data,
                                descriptor, this.descriptorsAreStillMatching);
                        message.add(field);
                        lineOffset += data.length()
                                + getDelimiterLength(oldDelimiter);
                    }
                }
            }
            if (message.getChildren().size() == config.getDescriptors().size()) {
                if (currentPosition + lineOffset < currentPosition
                        + currLine.length()) {
                    this.descriptorsAreStillMatching = false;
                    FieldDescriptor fd = new FieldDescriptor();
                    fd.setDelimiter("");
                    fd.setName("Failure");
                    List<FieldDescriptor> descriptors = config.getDescriptors();
                    fd.setType(descriptors.get(0).getType());
                    final Field field = new Field(currentPosition + lineOffset,
                            currLine.substring(lineOffset), fd,
                            this.descriptorsAreStillMatching);
                    message.add(field);
                }
                messages.add(message);
            }
        }
        currentPosition = currentPosition + currLine.length();
    }

    private int getDelimiterLength(String oldDelimiter) {
        int length = 0;
        for (int i = 0; i < oldDelimiter.length(); i++) {
            if (oldDelimiter.charAt(i) == '\\')
                continue;
            length++;
        }
        return length;
    }
}
