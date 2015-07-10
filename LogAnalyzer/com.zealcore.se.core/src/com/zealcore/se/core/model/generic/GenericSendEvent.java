package com.zealcore.se.core.model.generic;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.model.AbstractLogEvent;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ISequenceMember;
import com.zealcore.se.core.model.ISequenceMessage;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.SEProperty;

/**
 * GenericSendEvent are used together with {@link GenericReceiveEvent} and makes
 * it possible to set up message communication in the SequenceDiagram.
 * 
 * @author cafa
 * 
 */
public class GenericSendEvent extends AbstractLogEvent implements
        IGenericLogItem, ISequenceMessage {

    // Adapter start
    private GenericAdapter adapter = new GenericAdapter();

    private ISequenceMember recipent;

    private ISequenceMember sender;

    private long receivedAt = -1;

    private long receiverInstance = -1;

    private long drawingTs;

    private String message;

    public static final long INT_MAX = 4294967296L;

    private static final String RECEIVER_INST = "ReceiverInst";

    private static final String TYPE_NAME = "SendEvent";

    private static final String RECEIVER = "To";

    private static final String MESSAGE = "Message";

    /**
     * Constructor setting the event type name to "SendEvent".
     */
    public GenericSendEvent() {
        adapter.setTypeName(TYPE_NAME);
    }

    /**
     * Constructor setting the log event type name to "SendEvent" and its time
     * stamp.
     * 
     * @param ts
     *            is the log events time stamp.
     */
    public GenericSendEvent(final long ts) {
        adapter.setTypeName(TYPE_NAME);
        setTs(ts);
    }

    /**
     * Constructor setting the log event type name to "ReceiveEvent" and its
     * time stamp from a high and low part. The time stamp have a 32 + 32 bit
     * representation.
     * 
     * @param high
     *            is the high part of the time stamp.
     * @param low
     *            is the low part of the time stamp.
     */
    public GenericSendEvent(final long high, final long low) {
        adapter.setTypeName(TYPE_NAME);
        setTs(high * GenericSendEvent.INT_MAX + low);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.zealcore.se.core.model.generic.IGenericLogItem#addProperty(java.lang
     * .String, java.lang.Object)
     */
    public void addProperty(final String name, final Object value) {
        adapter.addProperty(name, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.AbstractObject#getZPropertyAnnotations()
     */
    @Override
    public List<SEProperty> getZPropertyAnnotations() {

        List<SEProperty> list = super.getZPropertyAnnotations();
        list.addAll(adapter.toSEProperties());

        return list;
    }

    /**
     * @param message
     *            is the message that is passed from the sender to the receiver.
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.ISequenceMessage#getMessage()
     */
    @ZCProperty(name = GenericSendEvent.MESSAGE, searchable = true)
    public String getMessage() {
        return this.message;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.zealcore.se.core.model.ISequenceMessage#setRecipent(com.zealcore.
     * se.core.model.ISequenceMember)
     */
    public void setRecipent(final ISequenceMember recipent) {
        this.recipent = recipent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.zealcore.se.core.model.ISequenceMessage#setSender(com.zealcore.se
     * .core.model.ISequenceMember)
     */
    public void setSender(final ISequenceMember sender) {
        this.sender = sender;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.ISequenceMessage#getRecipent()
     */
    @ZCProperty(name = GenericSendEvent.RECEIVER, searchable = true)
    public ISequenceMember getRecipent() {
        return recipent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.ISequenceMessage#getSender()
     */
    @ZCProperty(name = "From", description = "The name of the user that sent the message", searchable = true)
    public ISequenceMember getSender() {
        return sender;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.zealcore.se.core.model.generic.IGenericLogItem#getProperty(java.lang
     * .String)
     */
    public Object getProperty(final String key) {
        return adapter.getProperty(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.generic.IGenericLogItem#getTypeName()
     */
    public String getTypeName() {
        return adapter.getTypeName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.generic.IGenericLogItem#properties()
     */
    public Map<String, Object> properties() {
        return adapter.properties();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.AbstractObject#getType()
     */
    @Override
    public IType getType() {
        return GenericType.valueOf(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.zealcore.se.core.model.generic.IGenericLogItem#setTypeName(java.lang
     * .String)
     */
    public void setTypeName(final String string) {
        adapter.setTypeName(string);
    }

    /**
     * @param receivedAt
     *            is the time when the message was received
     */
    public void setDeliveryTime(final long receivedAt) {
        this.receivedAt = receivedAt;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.ISequenceMessage#getDeliveryTime()
     */
    @ZCProperty(name = "Received At", description = "The time in ns when the sent message was received", searchable = true, ts = true)
    public long getDeliveryTime() {
        return receivedAt;
    }

    /**
     * @return the time between the message was sent and received.
     */
    @ZCProperty(name = "Message Latency", description = "Latency from message send to receive", searchable = true, plottable = true, ts = true)
    public long getMessageLatency() {
        if (receivedAt == -1) {
            return 0;
        }
        return receivedAt - getTs();
    }

    /**
     * @return the instance or the receiver as a positive number if it is set,
     *         otherwise -1.
     */
    @ZCProperty(name = GenericSendEvent.RECEIVER_INST, searchable = false)
    public long getReciverInstance() {
        return this.receiverInstance;
    }

    /**
     * @param receiverInstance
     *            is the receiver instance.
     */
    public void setReceiverInstance(final long receiverInstance) {
        this.receiverInstance = receiverInstance;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.AbstractObject#referencedArtifacts()
     */
    @Override
    public Collection<IArtifact> referencedArtifacts() {
        final Collection<IArtifact> abstractArtifacts = super
                .referencedArtifacts();
        abstractArtifacts.add(this.recipent);
        abstractArtifacts.add(this.sender);
        return abstractArtifacts;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.zealcore.se.core.model.AbstractObject#substitute(com.zealcore.se.
     * core.model.IArtifact, com.zealcore.se.core.model.IArtifact)
     */
    @Override
    public void substitute(final IArtifact oldArtifact,
            final IArtifact newArtifact) {
        if (oldArtifact == getSender()) {
            setSender((ISequenceMember) newArtifact);
        }
        if (oldArtifact == getRecipent()) {
            setRecipent((ISequenceMember) newArtifact);
        } else {
            super.substitute(oldArtifact, newArtifact);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.AbstractLogEvent#toString()
     */
    @Override
    public String toString() {
        return this.getTypeName() + "[From=" + this.getSender().getName()
                + ", To=" + this.getRecipent().getName() + "]";
    }

    public long getDrawingTs() {
        return drawingTs;
    }

    public void setDrawingTs(long ts) {
        this.drawingTs = ts;
    }

}
