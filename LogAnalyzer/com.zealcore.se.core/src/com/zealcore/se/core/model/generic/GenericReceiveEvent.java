package com.zealcore.se.core.model.generic;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.zealcore.se.core.annotation.ZCProperty;
import com.zealcore.se.core.model.AbstractLogEvent;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ISequenceMember;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.SEProperty;

/**
 * GenericReceiveEvent are used together with {@link GenericSendEvent} and makes
 * it possible to set up message communication in the SequenceDiagram.
 * 
 * @author cafa
 * 
 */
public class GenericReceiveEvent extends AbstractLogEvent implements
        IGenericLogItem {

    public static final long INT_MAX = 4294967296L;

    private static final String MESSAGE = "Message";

    // Adapter start
    private GenericAdapter adapter = new GenericAdapter();

    private static final String TYPE_NAME = "ReceiveEvent";

    /*
     * The user that has caused this event
     */
    private IArtifact abstractResourceUser;

    private long sentAt = -1;

    private ISequenceMember receiver;

    private String message;

    /**
     * Constructor setting the log event type name to "ReceiveEvent".
     */
    /**
     * 
     */
    public GenericReceiveEvent() {
        adapter.setTypeName(TYPE_NAME);
    }

    /**
     * Constructor setting the log event type name to "ReceiveEvent" and its
     * time stamp.
     * 
     * @param ts
     *            is the log events time stamp.
     */
    public GenericReceiveEvent(final long ts) {
        super();
        adapter.setTypeName(TYPE_NAME);
        this.setTs(ts);
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
    public GenericReceiveEvent(final long high, final long low) {
        super();
        adapter.setTypeName(TYPE_NAME);
        this.setTs(high * GenericReceiveEvent.INT_MAX + low);
    }

    /**
     * @return the message receiver
     */
    public ISequenceMember getReceiver() {
        return this.receiver;
    }

    /**
     * @param receiver
     *            is the message receiver
     */
    public void setReceiver(final ISequenceMember receiver) {
        this.receiver = receiver;
    }

    /**
     * @param message
     *            is the message that is passed from the sender to the receiver.
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * @return the message sent from the sender to the receiver.
     */
    @ZCProperty(name = GenericReceiveEvent.MESSAGE, searchable = true)
    public String getMessage() {
        return this.message;
    }

    /**
     * Gets the name of the user which generated this event.
     * 
     * @return the AbstractResourceUser
     */
    @ZCProperty(name = "To", description = "The name of the user which generated this event", searchable = true)
    public IArtifact getResourceUser() {
        return this.abstractResourceUser;
    }

    /**
     * Sets the given user
     * 
     * @param abstractResourceUser
     *            the AbstractResourceUser
     */
    public void setResourceUser(final IArtifact abstractResourceUser) {
        this.abstractResourceUser = abstractResourceUser;
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
     * @see com.zealcore.se.core.model.AbstractObject#getType()
     */
    @Override
    public IType getType() {
        return GenericType.valueOf(this);
    }

    /**
     * This method is for internal use only {@inheritDoc}
     */
    public Map<String, Object> properties() {
        return adapter.properties();
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
     * @param sentAt
     *            is the time stamp when the message was sent.
     */
    public void setSentAt(final long sentAt) {
        this.sentAt = sentAt;
    }

    /**
     * @return the time stamp when the message was sent.
     */
    @ZCProperty(name = "Sent At", description = "The time in ns when the received message was sent", searchable = true, plottable = true)
    public long getSentAt() {
        return sentAt;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.AbstractObject#referencedArtifacts()
     */
    @Override
    public Collection<IArtifact> referencedArtifacts() {

        final Collection<IArtifact> refs = super.referencedArtifacts();
        refs.add(getReceiver());
        refs.add(getResourceUser());

        return refs;
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
        if (oldArtifact == this.receiver) {
            setReceiver((ISequenceMember) newArtifact);
        }
        if (oldArtifact == getResourceUser()) {
            setResourceUser(newArtifact);
        }
        super.substitute(oldArtifact, newArtifact);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.model.AbstractLogEvent#toString()
     */
    @Override
    public String toString() {
        StringBuilder value = new StringBuilder();
        value.append(this.getTypeName() + "[");

        Object sender = this.adapter.getProperty("From");
        if (sender != null) {
            value.append("From=" + sender + ", ");
        }
        value.append("To=" + this.getReceiver().getName() + "]");
        return value.toString();
    }
}
