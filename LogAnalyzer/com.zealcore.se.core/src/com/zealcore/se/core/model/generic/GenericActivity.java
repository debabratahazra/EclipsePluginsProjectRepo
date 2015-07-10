package com.zealcore.se.core.model.generic;

import java.util.List;
import java.util.Map;

import com.zealcore.se.core.model.AbstractActivity;
import com.zealcore.se.core.model.IProcessSwitch;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.SEProperty;

public class GenericActivity extends AbstractActivity implements
        IGenericLogItem {

    private static final String TYPE_NAME = "Activity";
    // Adapter start
    private GenericAdapter adapter = new GenericAdapter();

    public GenericActivity(final IProcessSwitch lastSwitchEvent,
            final IProcessSwitch sw) {
        setLogFile(lastSwitchEvent.getLogFile());
        setStartEvent(lastSwitchEvent);
        setStopEvent(sw);
        setOwner(lastSwitchEvent.getResourceUserIn());
        adapter.setTypeName(TYPE_NAME);
    }
    
    public GenericActivity(final IProcessSwitch sw) {
        setLogFile(sw.getLogFile());
        setStartEvent(sw);
        setOwner(sw.getResourceUserIn());
        adapter.setTypeName(TYPE_NAME);
    }

    public void addProperty(final String name, final Object value) {
        adapter.addProperty(name, value);
    }

    @Override
    public List<SEProperty> getZPropertyAnnotations() {

        List<SEProperty> list = super.getZPropertyAnnotations();
        list.addAll(adapter.toSEProperties());

        return list;
    }

    public void setTypeName(final String typeName) {
        adapter.setTypeName(typeName);
    }

    /**
     * This method is for internal use only {@inheritDoc}
     */
    public Map<String, Object> properties() {
        return adapter.properties();
    }

    @Override
    public IType getType() {
        return GenericType.valueOf(this);
    }

    public Object getProperty(final String key) {
        return adapter.getProperty(key);
    }

    public String getTypeName() {
        return adapter.getTypeName();
    }
}
