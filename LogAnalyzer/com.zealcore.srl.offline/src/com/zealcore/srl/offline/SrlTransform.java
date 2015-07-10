package com.zealcore.srl.offline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.MatchResult;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public final class SrlTransform {

    private static final String DELIMETER = "\\s*,\\s*";

    private static final String DECIMAL = "\\d+";

    private static final String NUMBER = "(" + DECIMAL + ")";

    private static final String NAME = "(\\S+(\\s+\\S+)*)";

    private static final String PARSE_EXPR = NUMBER + DELIMETER + NUMBER
            + DELIMETER + NAME;

    private static final String SRL_SUFFIX = ".srl";

    private static final int CLAZZ_TYPE = 0;

    private static final int SIGNAL_TYPE = 1;

    private static final int STATE_TYPE = 2;

    private static final String NEWLINE = "\n";

    private static final String BBTEXT = "bbtext";

    private static final String BRL = "brl";

    private static Map<String, IPrinter> printers = new HashMap<String, IPrinter>();

    private final HashMap<String, UmlStateMachine> stateMachineMap;

    public SrlTransform() {
        stateMachineMap = new HashMap<String, UmlStateMachine>();
        SrlTransform.printers.put(SrlTransform.BBTEXT, new BBTextPrinter());
        SrlTransform.printers.put(SrlTransform.BRL, new BinaryRosePrinter(
                stateMachineMap));
    }

    private static class Arguments {

        private String resourceFile;

        private String outputFile;

        private String inputFile;

        private String printer;

        private boolean verboseMode;

        private String xmiFile;

        public String getInputFile() {
            return inputFile;
        }

        public void setInputFile(final String inputFile) {
            this.inputFile = inputFile;
        }

        public String getOutputFile() {
            return outputFile;
        }

        public void setOutputFile(final String outputFile) {
            this.outputFile = outputFile;
        }

        public String getResourceFile() {
            return resourceFile;
        }

        public void setResourceFile(final String resourceFile) {
            this.resourceFile = resourceFile;
        }

        public void setPrinter(final String printer) {
            this.printer = printer;
        }

        public boolean isVerboseMode() {
            return verboseMode;
        }

        public void setVerboseMode(final boolean verboseMode) {
            this.verboseMode = verboseMode;
        }

        public void setXmiFile(final String xmiFile) {
            this.xmiFile = xmiFile;
        }

        public String getXmiFile() {
            return xmiFile;
        }
    }

    public static void main(final String[] args) {
        if (args.length <= 0) {
            SrlTransform.printUsage();
            return;
        }
        final Arguments arguments = SrlTransform.parseArguments(args);

        if (arguments.getInputFile() == null && arguments.getXmiFile() == null) {
            SrlTransform.printUsage();
            return;
        }

        if (arguments.getOutputFile() != null
                && arguments.getInputFile() != null
                && arguments.getInputFile().equalsIgnoreCase(
                        arguments.getOutputFile())) {
            Logger.log("Input file may not be the same as output file");
            SrlTransform.printUsage();
            return;
        }
        final SrlTransform transformer = new SrlTransform();
        transformer.transform(arguments);
    }

    public Blackbox transform(String inputFileName, String resourceFile, String xmiFile,
            boolean isVerboseMode) {
        final Blackbox blackbox = new Blackbox();
        blackbox.setVerboseMode(isVerboseMode);
        Logger.init(blackbox);
        if (inputFileName != null) {

            final ArrayList<ITransformer> transformers = new ArrayList<ITransformer>();

            transformers.add(new NativeReader(inputFileName));
            transformers.add(new FcpTransformer());
            transformers.add(new MetaTransformer());
            if (resourceFile != null) {
                final File file = new File(resourceFile);
                transformers.add(new Internalizer(file));
            }
            transformers.add(new TimestampTransformer());

            if (xmiFile != null) {
                final XmiAdapter adapt = new XmiAdapter(stateMachineMap);
                // adapt.setOut(out);
                try {
                    adapt.parse(new File(xmiFile));
                } catch (final ParserConfigurationException e) {
                    Logger.err("ParserConfigurationException, failed to parse XMI file:"
                            + xmiFile
                            + SrlTransform.NEWLINE
                            + e.getMessage());
                } catch (final SAXException e) {
                    Logger.err("SAXException, failed to parse XMI file:"
                            + xmiFile + SrlTransform.NEWLINE
                            + e.getMessage());
                } catch (final IOException e) {
                    Logger.err("IOException, failed to parse XMI file:"
                            + xmiFile + SrlTransform.NEWLINE
                            + e.getMessage());
                }
            }

            String mentorIndexFileName = getIndexFileName(inputFileName);
            if (mentorIndexFileName != null) {
                File mentorIndexFile = new File(mentorIndexFileName);
                if (mentorIndexFile.exists()) {
                    InputStreamReader reader = openFile(mentorIndexFile);
                    if (reader != null) {
                        BufferedReader br = new BufferedReader(reader);
                        String line = null;
                        int lineNr = 0;
                        try {
                            while (true) {
                                line = br.readLine();
                                lineNr++;
                                if (line == null) {
                                    break;
                                }
                                if (line.startsWith("#")) {
                                    continue;
                                }
                                if (line.equals("")) {
                                    continue;
                                }
                                Scanner scanner = new Scanner(line);
                                scanner.findInLine(PARSE_EXPR);
                                MatchResult result = scanner.match();
                                int groupCount = result.groupCount();
                                if (groupCount != 4) {
                                    throw new IllegalStateException(
                                            "Wrong nr of parameters in index file: "
                                                    + mentorIndexFileName
                                                    + "on line nr " + lineNr);
                                }
                                int type = Integer.parseInt(result.group(1));
                                int id = Integer.parseInt(result.group(2));
                                String name = result.group(3);
                                MentorIndex mentorIndex = blackbox.getMentorIndex();
                                switch (type) {
                                case CLAZZ_TYPE:
                                    mentorIndex.putClazz(id, name);
                                    break;
                                case SIGNAL_TYPE:
                                case 3:
                                    mentorIndex.putSignal(id, name);
                                    break;
                                case STATE_TYPE:
                                case 4:
                                    mentorIndex.putState(id, name);
                                    break;
                                default:
                                    Logger.err("MentorIndex: unknown type = "
                                            + type);
                                }
                            }
                        } catch (IOException e) {
                            Logger.err("MentorIndex: IOException");
                        } finally {
                            if (br != null) {
                                try {
                                    br.close();
                                } catch (IOException e) {
                                    Logger.err("MentorIndex: Unable to close file");
                                }
                            }
                        }
                    }
                }
            }
            for (final ITransformer transformer : transformers) {
                transformer.transform(blackbox);
                if (blackbox.isVerboseMode()) {
                    Logger.log("Log after transformer: "
                            + transformer.toString());
                    Logger.log("---------------------------------------------------------------------");
                    Logger.log(blackbox);
                } else {
                    Logger.log(blackbox.getInfo());
                }
            }
        }
        return blackbox;
    }

    private void transform(final Arguments arguments) {
        final Blackbox blackbox = new Blackbox();
        blackbox.setVerboseMode(arguments.isVerboseMode());
        Logger.init(blackbox);
        String inputFileName = arguments.getInputFile();
        if (inputFileName != null) {

            final ArrayList<ITransformer> transformers = new ArrayList<ITransformer>();

            transformers.add(new NativeReader(inputFileName));
            transformers.add(new FcpTransformer());
            transformers.add(new MetaTransformer());
            if (arguments.getResourceFile() != null) {
                final File file = new File(arguments.getResourceFile());
                transformers.add(new Internalizer(file));
            }
            transformers.add(new TimestampTransformer());

            for (final ITransformer transformer : transformers) {
                transformer.transform(blackbox);
                if (blackbox.isVerboseMode()) {
                    Logger.log("Log after transformer: "
                            + transformer.toString());
                    Logger.log("---------------------------------------------------------------------");
                    Logger.log(blackbox);
                } else {
                    Logger.log(blackbox.getInfo());
                }
            }
        }
        if (arguments.printer == null) {
            arguments.printer = SrlTransform.BRL;
        }
        final IPrinter printer = SrlTransform.printers.get(arguments.printer);

        PrintStream out = System.out;
        if (arguments.getOutputFile() != null) {
            try {
                out = new PrintStream(arguments.getOutputFile());
            } catch (final FileNotFoundException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        printer.setOut(out);

        if (arguments.getXmiFile() != null) {
            final XmiAdapter adapt = new XmiAdapter(stateMachineMap);
            // adapt.setOut(out);
            try {
                adapt.parse(new File(arguments.getXmiFile()));
            } catch (final ParserConfigurationException e) {
                Logger.err("ParserConfigurationException, failed to parse XMI file:"
                        + arguments.getXmiFile()
                        + SrlTransform.NEWLINE
                        + e.getMessage());
            } catch (final SAXException e) {
                Logger.err("SAXException, failed to parse XMI file:"
                        + arguments.getXmiFile() + SrlTransform.NEWLINE
                        + e.getMessage());
            } catch (final IOException e) {
                Logger.err("IOException, failed to parse XMI file:"
                        + arguments.getXmiFile() + SrlTransform.NEWLINE
                        + e.getMessage());
            }
        }

        String mentorIndexFileName = getIndexFileName(inputFileName);
        if (mentorIndexFileName != null) {
            File mentorIndexFile = new File(mentorIndexFileName);
            if (mentorIndexFile.exists()) {
                InputStreamReader reader = openFile(mentorIndexFile);
                if (reader != null) {
                    BufferedReader br = new BufferedReader(reader);
                    String line = null;
                    int lineNr = 0;
                    try {
                        while (true) {
                            line = br.readLine();
                            lineNr++;
                            if (line == null) {
                                break;
                            }
                            if (line.startsWith("#")) {
                                continue;
                            }
                            if (line.equals("")) {
                                continue;
                            }
                            Scanner scanner = new Scanner(line);
                            scanner.findInLine(PARSE_EXPR);
                            MatchResult result = scanner.match();
                            int groupCount = result.groupCount();
                            if (groupCount != 4) {
                                throw new IllegalStateException(
                                        "Wrong nr of parameters in index file: "
                                                + mentorIndexFileName
                                                + "on line nr " + lineNr);
                            }
                            int type = Integer.parseInt(result.group(1));
                            int id = Integer.parseInt(result.group(2));
                            String name = result.group(3);
                            MentorIndex mentorIndex = blackbox.getMentorIndex();
                            switch (type) {
                            case CLAZZ_TYPE:
                                mentorIndex.putClazz(id, name);
                                break;
                            case SIGNAL_TYPE:
                            case 3:
                                mentorIndex.putSignal(id, name);
                                break;
                            case STATE_TYPE:
                            case 4:
                                mentorIndex.putState(id, name);
                                break;
                            default:
                                Logger.err("MentorIndex: unknown type = "
                                        + type);
                            }
                        }
                    } catch (IOException e) {
                        Logger.err("MentorIndex: IOException");
                    } finally {
                        if (br != null) {
                            try {
                                br.close();
                            } catch (IOException e) {
                                Logger.err("MentorIndex: Unable to close file");
                            }
                        }
                    }
                }
            }
        }
        printer.transform(blackbox);

        blackbox.getMentorIndex().clear();
        out.close();
    }

    private String getIndexFileName(final String inputFileName) {
        if (!inputFileName.endsWith(SRL_SUFFIX)) {
            return null;
        }
        int i = inputFileName.lastIndexOf(SRL_SUFFIX);
        if (i == -1) {
            throw new AssertionError(
                    "inputFileName does not contain '.srl' even though it ends with it");
        }
        String baseFileName = inputFileName.substring(0, i);
        if (baseFileName == null) {
            throw new AssertionError();
        }
        return baseFileName + ".idx";
    }

    private InputStreamReader openFile(final File mentorIndexFile) {
        InputStreamReader in = null;
        try {
            try {
                in = new InputStreamReader(
                        new FileInputStream(mentorIndexFile), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                Logger.err("UnsupportedEncodingException, failed create InputStreamReader for "
                        + mentorIndexFile);
            }
        } catch (FileNotFoundException e) {
            Logger.err("FileNotFoundException, failed to open "
                    + mentorIndexFile);
        }
        return in;
    }

    private static Arguments parseArguments(final String[] args) {
        // TODO: throw illegalargument exception if any arguments
        // are illformed etc
        final Arguments arguments = new Arguments();
        for (final String arg : args) {
            if (arg.startsWith("-e")) {
                arguments.setResourceFile(arg.substring(2));
            } else if (arg.startsWith("-o")) {
                arguments.setOutputFile(arg.substring(2));
            } else if (arg.startsWith("-p")) {
                arguments.setPrinter(arg.substring(2));
            } else if (arg.startsWith("-x")) {
                arguments.setXmiFile(arg.substring(2));
            } else if (arg.startsWith("-v")) {
                arguments.setVerboseMode(true);
            } else {
                arguments.setInputFile(arg);
            }
        }
        return arguments;
    }

    private static void printUsage() {
        System.out.println("Usage: -e [optional external resource file]");
        System.out.println("       -o [optional output file]");
        System.out.println("       -x [optional StateMachine XMI file]");
        System.out.println("       -p [optional printer clf|bbtext|brl ]");
        System.out.println("       [system recorder log file]");
        System.out.println("       -v [optional verbose mode]\n");
    }
}
