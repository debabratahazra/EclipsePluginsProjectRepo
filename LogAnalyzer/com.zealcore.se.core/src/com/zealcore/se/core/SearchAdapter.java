package com.zealcore.se.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.ui.IMemento;

import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.IProperty;
import com.zealcore.se.core.model.ISequence;
import com.zealcore.se.core.model.ISequenceMember;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.LogFile;
import com.zealcore.se.core.model.SEProperty;
import com.zealcore.se.core.model.generic.GenericType;
import com.zealcore.se.core.model.generic.IGenericLogItem;

/**
 * 
 * @see SearchCriteria
 * 
 * @author pasa
 * @version 2.0
 * @since 1.0
 * 
 */
public final class SearchAdapter implements ISearchAdapter {
    private static final String FAILED_INSTANTIATE_CLASS = "Failed instantiate class ";

    private static final String WHITE_SPACE = " ";

    private static final String CRITERIA_NODE = "criteria";

    private static final String TAG_TYPE_ID = "tested-class";

    public static final String STRING_REGEXP = "(.*)";

    public static final String NUMERIC_OPERATORS = "(<|>|<=|>=|=|!=)?";

    public static final String INT_REGEXP = SearchAdapter.NUMERIC_OPERATORS
            + "(-?[0-9]{1,})";

    public static final String FLOAT_REGEXP = SearchAdapter.NUMERIC_OPERATORS
            + "(-?[0-9]{1,})(.)?([0-9]{1,})";

    private static final String BASECLASS = "baseclass";

    private static final String GENERIC_PROPERTY_RETVAL = "generic-property-value";

    private static final String GENERIC_PROPERTY_NAME = "generic-property-name";

    private static final String IS_GENERIC = "is-generic";

    private static final String PARAMS = "parameters";

    private static final String TYPENAME = "typename";

    public static SearchAdapter createSearchAdapter(final IType type) {
        final SearchAdapter sa = new SearchAdapter(type);
        sa.setCritList(sa.createSearchCriteries(type));
        sa.sort();
        return sa;
    }

    public static SearchAdapter valueOf(final IMemento memento,
            final ITypeRegistry typeService) {
        final String strTypeId = memento.getString(SearchAdapter.TAG_TYPE_ID);
        final boolean isGeneric = memento.getBoolean(SearchAdapter.IS_GENERIC);
        IType type = null;
        boolean unknownType = false;
        try {
            type = typeService.getType(strTypeId);
        } catch (IllegalArgumentException e) {
            unknownType = true;
        }
        if (unknownType) {
            if (isGeneric) {
                String className = memento.getString(SearchAdapter.BASECLASS);
                String typeName = memento.getString(SearchAdapter.TYPENAME);
                Class<?> clazz;
                try {
                    clazz = Class.forName(className);

                } catch (ClassNotFoundException e1) {
                    throw new RuntimeException("Failed to get base class for "
                            + className, e1);
                }
                Object newInstance;
                try {
                    newInstance = clazz.newInstance();
                } catch (InstantiationException e1) {
                    throw new RuntimeException(FAILED_INSTANTIATE_CLASS
                            + className, e1);
                } catch (IllegalAccessException e1) {
                    throw new RuntimeException(FAILED_INSTANTIATE_CLASS
                            + className, e1);
                }
                if (newInstance instanceof IGenericLogItem) {
                    IGenericLogItem gen = (IGenericLogItem) newInstance;
                    gen.setTypeName(typeName);
                    for (final IMemento paramsMemento : memento
                            .getChildren(SearchAdapter.PARAMS)) {
                        String propName = paramsMemento
                                .getString(SearchAdapter.GENERIC_PROPERTY_NAME);
                        String retval = paramsMemento
                                .getString(SearchAdapter.GENERIC_PROPERTY_RETVAL);
                        /*
                         * Simple check to avoid primitive types (not dynamic)
                         */
                        if (retval != null && retval.length() > 6) {
                            if ("java.lang.Integer".equals(retval)) {
                                gen.addProperty(propName, new Integer(0));
                            } else if ("java.lang.Float".equals(retval)) {
                                gen.addProperty(propName, new Float(0));
                            } else if ("java.lang.Double".equals(retval)) {
                                gen.addProperty(propName, new Double(0));
                            } else if ("java.lang.Long".equals(retval)) {
                                gen.addProperty(propName, new Long(0));
                            } else if ("java.lang.String".equals(retval)) {
                                gen.addProperty(propName, new String(""));
                            } else if ("java.lang.Boolean".equals(retval)) {
                                gen.addProperty(propName, new Boolean(false));
                            } else if ("java.lang.Short".equals(retval)) {
                                short s = 0;
                                gen.addProperty(propName, new Short(s));
                            } else if ("com.zealcore.se.core.model.IArtifact"
                                    .equals(retval)) {
                                gen.addProperty(propName, new IArtifact() {

                                    public String getId() {
                                        return "";
                                    }

                                    public String getName() {
                                        return "";
                                    }

                                    public boolean isRoot() {
                                        return false;
                                    }

                                    public void setName(final String name) {}

                                    public void setRoot(final boolean root) {}

                                    public String getClassName() {
                                        return "";
                                    }

                                    public LogFile getLogFile() {
                                        return null;
                                    }

                                    public IType getType() {
                                        return null;
                                    }

                                    public List<SEProperty> getZPropertyAnnotations() {
                                        return null;
                                    }

                                    public Collection<IArtifact> referencedArtifacts() {
                                        return null;
                                    }

                                    public void setLogFile(final LogFile logFile) {}

                                    public int compareTo(final Object o) {
                                        return 0;
                                    }

                                    public void substitute(
                                            final IArtifact oldArtifact,
                                            final IArtifact newArtifact) {}
                                });
                            } else if ("com.zealcore.se.core.model.ISequence"
                                    .equals(retval)) {
                                gen.addProperty(propName, new ISequence() {

                                    public String getId() {
                                        return "";
                                    }

                                    public String getName() {
                                        return "";
                                    }

                                    public boolean isRoot() {
                                        return false;
                                    }

                                    public void setName(final String name) {}

                                    public void setRoot(final boolean root) {}

                                    public String getClassName() {
                                        return "";
                                    }

                                    public LogFile getLogFile() {
                                        return null;
                                    }

                                    public IType getType() {
                                        return null;
                                    }

                                    public List<SEProperty> getZPropertyAnnotations() {
                                        return null;
                                    }

                                    public Collection<IArtifact> referencedArtifacts() {
                                        return null;
                                    }

                                    public void setLogFile(final LogFile logFile) {}

                                    public void substitute(
                                            final IArtifact oldArtifact,
                                            final IArtifact newArtifact) {}

                                    public int compareTo(final Object o) {
                                        return 0;
                                    }
                                });
                            } else if ("com.zealcore.se.core.model.ISequenceMember"
                                    .equals(retval)) {
                                gen.addProperty(propName,
                                        new ISequenceMember() {

                                            public String getName() {
                                                return "";
                                            }

                                            public ISequence getParent() {
                                                return null;
                                            }

                                            public void setParent(
                                                    final ISequence parent) {}

                                            public String getId() {
                                                return "";
                                            }

                                            public boolean isRoot() {
                                                return false;
                                            }

                                            public void setName(
                                                    final String name) {}

                                            public void setRoot(
                                                    final boolean root) {}

                                            public String getClassName() {
                                                return "";
                                            }

                                            public LogFile getLogFile() {
                                                return null;
                                            }

                                            public IType getType() {
                                                return null;
                                            }

                                            public List<SEProperty> getZPropertyAnnotations() {
                                                return null;
                                            }

                                            public Collection<IArtifact> referencedArtifacts() {
                                                return null;
                                            }

                                            public void setLogFile(
                                                    final LogFile logFile) {}

                                            public void substitute(
                                                    final IArtifact oldArtifact,
                                                    final IArtifact newArtifact) {}

                                            public int compareTo(final Object o) {
                                                return 0;
                                            }
                                        });

                            } else {
                                try {
                                    gen.addProperty(propName, Class.forName(
                                            retval).newInstance());
                                } catch (InstantiationException e1) {
                                    throw new RuntimeException(e1);
                                } catch (IllegalAccessException e1) {
                                    throw new RuntimeException(e1);
                                } catch (ClassNotFoundException e1) {
                                    throw new RuntimeException(e1);
                                }
                            }
                        }
                    }
                    type = gen.getType();
                }
            } else {
                throw new IllegalStateException("Unable to retrieve type "
                        + strTypeId);
            }
        }

        final SearchAdapter adapter = SearchAdapter.createSearchAdapter(type);

        final Map<String, SearchCriteria> map = new HashMap<String, SearchCriteria>();
        // Find saved criterias
        for (final IMemento criteriaMemento : memento
                .getChildren(SearchAdapter.CRITERIA_NODE)) {
            final SearchCriteria criteria = SearchCriteria.valueOf(type,
                    criteriaMemento);
            map.put(criteria.getAttributeName(), criteria);
        }

        for (final SearchCriteria criteria : adapter.getCritList()) {
            final SearchCriteria saved = map.get(criteria.getAttributeName());
            if (saved != null) {
                criteria.setOperand1(saved.getOperand1());
                criteria.setOperator1(saved.getBinOperator1());
                criteria.setOperand2(saved.getOperand2());
                // criteria.setOperator2(saved.getBinOperator2());
                criteria.setWildcard(saved.getWildcard());
            }
        }

        adapter.sort();
        return adapter;
    }

    /*
     * A list of search criterias.
     */
    private List<SearchCriteria> critList = new ArrayList<SearchCriteria>();

    private IType searchType;

    /**
     * Creates a SearchAdapter with a specified Type. This constructor is
     * private, because the users of the public interface shall use the static
     * factory method createSearchAdapter() to create adapters.
     * 
     */
    private SearchAdapter(final IType type) {
        this.searchType = type;
    }

    /**
     * Searches the given class for ZSearchableProperty annotations and creates
     * a SearchCriteria for each annotated attribute that is found. This method
     * is private and should not be called directly by the user. The users of
     * the public interface shall use the static factory method
     * createSearchAdapter.
     * 
     * @param type
     *            the type to be searched for
     * @return a list of search criterias. Is an empty list of no searchable
     *         properties were found
     */
    private List<SearchCriteria> createSearchCriteries(final IType type) {
        this.critList = new ArrayList<SearchCriteria>();
        for (final IProperty property : type.getProperties()) {
            if (property.isSearchable()) {
                this.critList.add(new SearchCriteria(property));
            }
        }
        sort();

        return this.critList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.ISearchAdapter#getCritList()
     */
    public List<SearchCriteria> getCritList() {
        return Collections.unmodifiableList(this.critList);
    }

    public IType getSearchType() {
        return this.searchType;
    }

    public void saveState(final IMemento matcherMemento) {
        matcherMemento.putString(SearchAdapter.TAG_TYPE_ID, getSearchType()
                .getId());
        IType type = getSearchType();

        if (type instanceof GenericType) {

            GenericType gt = (GenericType) type;
            matcherMemento.putBoolean(SearchAdapter.IS_GENERIC, true);
            Class<? extends IGenericLogItem> baseClass = gt.getBaseClass();

            matcherMemento.putString(SearchAdapter.TYPENAME, type.getName());

            matcherMemento.putString(SearchAdapter.BASECLASS, baseClass
                    .getCanonicalName());

            Collection<IProperty> properties = gt.getProperties();

            for (IProperty prop : properties) {
                if (prop.isGeneric()) {
                    final IMemento paramsMemento = matcherMemento
                            .createChild(SearchAdapter.PARAMS);
                    paramsMemento
                            .putString(SearchAdapter.GENERIC_PROPERTY_NAME,
                                    prop.getName());
                    paramsMemento.putString(
                            SearchAdapter.GENERIC_PROPERTY_RETVAL, prop
                                    .getReturnType().getCanonicalName());
                }
            }

        } else {
            matcherMemento.putBoolean(SearchAdapter.IS_GENERIC, false);
        }
        for (final SearchCriteria criteria : this.critList) {
            if ((criteria.getOperand1() != null && !criteria.getOperand1()
                    .equals(""))
                    || (criteria.getOperand2() != null && !criteria
                            .getOperand2().equals(""))) {
                final IMemento criteriaMemento = matcherMemento
                        .createChild(SearchAdapter.CRITERIA_NODE);
                criteria.saveState(criteriaMemento);
            }
        }
    }

    /**
     * Sets the search criterias of this search adapter. This is for internal
     * use only
     * 
     * @param critList
     *            a list of search critierias.
     */
    public void setCritList(final List<SearchCriteria> critList) {
        this.critList = critList;
    }

    private void sort() {
        Collections.sort(this.critList, new Comparator<SearchCriteria>() {
            public int compare(final SearchCriteria o1, final SearchCriteria o2) {
                return o1.getAttributeName().compareTo(o2.getAttributeName());
            }
        });
    }

    /**
     * toString method for SearchAdapter.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[SearchType=" + getName()
                + ",Criterias" + getCritList() + ']';
    }

    public boolean filter(final IObject x) {
        final IType type = x.getType();
        if (!type.isA(getSearchType())) {
            return false;
        }

        for (final SearchCriteria criteria : this.critList) {
            if (!criteria.matches(x)) {
                return false;
            }
        }
        return true;
    }

    public String getName() {
        return getSearchType().getName();
    }

    public String getCondition() {
        String conditionStr = new String();
        for (final SearchCriteria sc : getCritList()) {
            boolean hasValue = false;
            if (sc.getOperand1() != null && !sc.getOperand1().equals("")) {
                if (conditionStr.length() > 0) {
                    conditionStr += "; ";
                }
                conditionStr += sc.getAttributeName()
                        + SearchAdapter.WHITE_SPACE;
                if (sc.getBinOperator1() != null && !sc.getBinOperator1().contains("LIKE")) {
                    conditionStr += sc.getBinOperator1()
                            + SearchAdapter.WHITE_SPACE;
                }
                conditionStr += sc.getOperand1();
                hasValue = true;
            }
            if (sc.getOperand2() != null && !sc.getOperand2().equals("")) {
                if (conditionStr.length() > 0) {
                    conditionStr += ", ";
                }
                conditionStr += sc.getAttributeName()
                        + SearchAdapter.WHITE_SPACE;
                if (sc.getBinOperator2() != null && !sc.getBinOperator2().contains("LIKE")) {
                    conditionStr += sc.getBinOperator2()
                            + SearchAdapter.WHITE_SPACE;
                }
                conditionStr += sc.getOperand2();
                hasValue = true;
            }
            if (hasValue && sc.getWildcard()) {
                
                conditionStr += " (NOT)";
            }
        }
        return conditionStr;
    }
}
