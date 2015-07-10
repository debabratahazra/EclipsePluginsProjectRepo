package com.zealcore.srl.offline;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Blackbox {

    private static final String DASHES = "----------------------------------------\n";

    private final Map<Integer, Struct> structMap = new HashMap<Integer, Struct>();

    private final Map<Integer, Type> typeMap = new HashMap<Integer, Type>();

    private static final String NEWLINE = "\n";

    private Collection<LinearMessage> linearMessages = new ArrayList<LinearMessage>();

    private Collection<CircularMessage> circularMessages = new ArrayList<CircularMessage>();

    private Collection<TypedLinearMessage> typedLinearMessages = new ArrayList<TypedLinearMessage>();

    private Collection<TypedCircularMessage> typedCircularMessages = new ArrayList<TypedCircularMessage>();

    private long bufferSize;

    private long checksum;

    private long magicFunction;

    private long magicString;

    private long magicPhys;

    private long elemCount;

    private long minCircularBufElemCount;

    private long iBorder;

    private long iEnd;

    private long iNext;
    
    private long iNextOriginal = -1;

    private ByteOrder byteOrder;

    private long magicArray;

    private boolean verboseMode;

    private MentorIndex mentorIndex = new MentorIndex();

    private Version version;

    private Long cpuTickFreq;

    private boolean circularReadStarted;
    
    boolean isCircularReadStarted() {
        return circularReadStarted;
    }
    
    void startCircularRead() {
        circularReadStarted = true;
    }
    
    void stopCircularRead() {
        circularReadStarted = false;
    }
    
    public boolean isVerboseMode() {
        return verboseMode;
    }

    public void setVerboseMode(final boolean verboseMode) {
        this.verboseMode = verboseMode;
    }

    public void addLinearMessages(final Collection<LinearMessage> messages) {
        linearMessages.addAll(messages);
    }

    public void addCircularMessages(final Collection<CircularMessage> messages) {
        circularMessages.addAll(messages);
    }

    public void setBufferSize(final long bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void setMagicFunction(final long magicFunction) {
        this.magicFunction = magicFunction;
    }

    public void setMagicString(final long magicString) {
        this.magicString = magicString;
    }

    public Object getChecksum() {
        return checksum;
    }

    public void setChecksum(final long checksum) {
        this.checksum = checksum;
    }

    public Collection<CircularMessage> getCircularMessages() {
        return circularMessages;
    }

    public void setCircularMessages(
            final Collection<CircularMessage> circularMessages) {
        this.circularMessages = circularMessages;
    }

    public Collection<LinearMessage> getLinearMessages() {
        return linearMessages;
    }

    public void setLinearMessages(final Collection<LinearMessage> linearMessages) {
        this.linearMessages = linearMessages;
    }

    public void addTypedLinearMessage(final TypedLinearMessage typedMessage) {
        typedLinearMessages.add(typedMessage);
    }

    public void addTypedCircularMessage(final TypedCircularMessage typedMessage) {
        typedCircularMessages.add(typedMessage);
    }

    public long getBufferSize() {
        return bufferSize;
    }

    public long getMagicFunction() {
        return magicFunction;
    }

    public long getMagicString() {
        return magicString;
    }


    public void setVersion(final Version version) {
        this.version = version;
        
    }

    public void setMinCircularBufElemCount(final long minCircularBufElemCount) {
        this.minCircularBufElemCount = minCircularBufElemCount;
    }

    public void setMarginElemCount(final long marginElemCount) {

    }

    public void setIBorder(final long iBorder) {
        this.iBorder = iBorder;
    }

    public void setElemCount(final long elemCount) {
        this.elemCount = elemCount;
    }

    public void setIEnd(final long iEnd) {
        this.iEnd = iEnd;
    }

    public void setINext(final long iNext) {
        this.iNext = iNext;
    }

    public long getIBorder() {
        return iBorder;
    }

    public long getElemCount() {
        return elemCount;
    }

    public long getIEnd() {
        return iEnd;
    }

    public long getINext() {
        return iNext;
    }

    public long getMinCircularBufElemCount() {
        return minCircularBufElemCount;
    }

    public ByteOrder getByteOrder() {
        return byteOrder;
    }

    public void setByteOrder(final ByteOrder byteOrder) {
        this.byteOrder = byteOrder;
    }

    void addStruct(final int id, final Struct struct) {
        structMap.put(id, struct);
    }

    Struct getStruct(final int id) {
        return structMap.get(id);
    }

    public Collection<Struct> getStructs() {
        return Collections.unmodifiableCollection(structMap.values());
    }

    public Collection<Type> getTypes() {
        return Collections.unmodifiableCollection(typeMap.values());
    }

    @Override
    public String toString() {
        final StringBuilder output = new StringBuilder();
        output.append("\nLinear messages, " + linearMessages.size() + NEWLINE);
        output.append(DASHES);
        for (final LinearMessage message : getLinearMessages()) {
            output.append(message + NEWLINE);
        }
        output.append("\nCircular messages, " + circularMessages.size()
                + NEWLINE);
        output.append(DASHES);
        for (final CircularMessage message : getCircularMessages()) {
            output.append(message + NEWLINE);
        }

        output.append("\nTypes, " + getTypes().size() + NEWLINE);
        output.append(DASHES);
        for (final Type t : getTypes()) {
            output.append(t + NEWLINE);
        }

        output.append("\nStructs, " + getStructs().size() + NEWLINE);
        output.append(DASHES);
        for (final Struct s : getStructs()) {
            output.append(s + NEWLINE);
        }

        Logger.log("Typed Linear Message");
        Logger.log(DASHES);
        for (final TypedLinearMessage message : getTypedLinearMessages()) {
            output.append(message);

        }
        Logger.log("Typed Circular Message");
        Logger.log(DASHES);
        for (final TypedCircularMessage message : getTypedCircularMessages()) {
            output.append(message);
            output.append(NEWLINE);
        }

        return output.toString();
    }

    public String getInfo() {
        final StringBuilder output = new StringBuilder();
        output.append("Linear messages:      " + linearMessages.size()
                + NEWLINE);
        output.append("Circular messages:    " + circularMessages.size()
                + NEWLINE);
        output.append("Structs:              " + getStructs().size() + NEWLINE);
        output.append("Typed Linear Message: "
                + getTypedLinearMessages().size() + NEWLINE);
        output.append("Typed Circular Message: "
                + getTypedCircularMessages().size() + NEWLINE);

        return output.toString();
    }

    public Collection<TypedCircularMessage> getTypedCircularMessages() {
        return typedCircularMessages;
    }

    public void setTypedCircularMessages(
            final Collection<TypedCircularMessage> typedCircularMessages) {
        this.typedCircularMessages = typedCircularMessages;
    }

    public Collection<TypedLinearMessage> getTypedLinearMessages() {
        return typedLinearMessages;
    }

    public void setTypedLinearMessages(
            final Collection<TypedLinearMessage> typedLinearMessages) {
        this.typedLinearMessages = typedLinearMessages;
    }

    public long getMagicPhys() {
        return magicPhys;
    }

    public void setMagicPhys(final long magicPhys) {
        this.magicPhys = magicPhys;
    }

    public void setMagicArray(final long array) {
        magicArray = array;
    }

    public long getMagicArray() {
        return magicArray;
    }

    public void addType(final int id, final Type t) {
        typeMap.put(id, t);
    }

    public Type getType(final int id) {
        return typeMap.get(id);
    }

    public MentorIndex getMentorIndex() {
        return mentorIndex;
        
    }

    public Version getVersion() {
        return version;
    }

    void setCpuTickFreq(final Long cpuTickFreq) {
        this.cpuTickFreq = cpuTickFreq;
    }
    
    public Long getCpuTickFreq() {
        return cpuTickFreq;
    }
    
    long getINextOriginal() {
        return iNextOriginal;
    }

    public void setINextOriginal(final long iNext) {
        iNextOriginal = iNext;
    }
}
