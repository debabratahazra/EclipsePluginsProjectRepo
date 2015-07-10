/*
 * 
 */
package com.zealcore.se.core.tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.core.model.LogFile;
import com.zealcore.se.core.model.generic.IGenericLogItem;

public final class Util {
    private static final String NEWLINE = System.getProperty("line.separator");

    private static Set<IObject> items = new HashSet<IObject>();

    private static List<Method> objectMethods = Arrays.asList(Object.class
            .getMethods());

    private Util() {

    }

    private static void clearHistory() {

        Util.items.clear();
    }

    /**
     * Checks element integrity. The integrity of an item is defined such that
     * an item may not be null, have a null valued logfile. A item may not have
     * any methods that are defined to return {@link IObject}s
     * which are null (for instance AbsractDuration#getStopEvent) may not return
     * null. Optionally it this method also checks if the logsession is null and
     * if the logsessions name is longer than 1.
     * 
     * 
     * @param item
     *                the item to check element integrity
     * @param checksession
     *                the checksession flag
     * 
     * @throws IllegalAccessException
     *                 the illegal access exception
     * @throws InvocationTargetException
     *                 the invocation target exception
     */
    public static synchronized void checkElementIntegrity(
            final IObject item, final boolean checksession)
            throws IllegalAccessException, InvocationTargetException {

        Util.clearHistory();
        Util.check(item, checksession);
    }

    private static void check(final IObject item,
            final boolean checksession) throws IllegalAccessException,
            InvocationTargetException {

        if (item == null) {
            throw new NullPointerException("Item is null");
        }

        if (Util.items.contains(item)) {
            return;
        }
        Util.items.add(item);

        if (item.getLogFile() == null) {
            throw new IllegalStateException(item.getClass()
                    + ". Logfile is null: item = " + item);
        }

        if (item.getLogFile().equals(LogFile.DEFAULT)) {
            throw new IllegalStateException(
                    "Logfile may never be the default:   item=" + item
                            + " class= " + item.getClass());
        }

        if (item instanceof IArtifact) {
            final IArtifact artifact = (IArtifact) item;
            if (artifact.getName() == null) {
                throw new IllegalArgumentException(
                        "An artifact may not have NULL name " + artifact
                                + ". class=" + artifact.getClass().getName());
            }

        }

        IType type = item.getType();

        if (type == null) {
            throw new IllegalStateException(
                    item.getClass()
                            + " does not provide a valid IType definition (item.getType()");

        }
        if (type.getName() == null || type.getName().length() < 1) {
            throw new IllegalStateException("The type of the item "
                    + item.getClass() + " has no specific name. Name = "
                    + type.getName());
        }
        checkGenericItems(item);

        checkReferencedArtifacts(item, checksession);
    }

    private static void checkGenericItems(final IObject item)
            throws IllegalAccessException, InvocationTargetException {
        /*
         * Check generic events and artifacts
         */
        if (item instanceof IGenericLogItem) {
            IGenericLogItem igc = (IGenericLogItem) item;

            for (Entry<String, Object> prop : igc.properties().entrySet()) {
                String name = prop.getKey();
                Object value = prop.getValue();
                if (value == null || name == null) {
                    throw new IllegalStateException(" In class "
                            + item.getClass().getSimpleName()
                            + " the property " + name
                            + " is not allowed to return or be null");
                }

                if (value instanceof IObject) {
                    IObject obj = (IObject) value;
                    checkElementIntegrity(obj, false);

                }
            }
        }
    }

    private static void checkReferencedArtifacts(
            final IObject item, final boolean checksession)
            throws IllegalAccessException, InvocationTargetException {
        final Collection<IArtifact> referenced = item
                .referencedArtifacts();
        for (final Method m : item.getClass().getMethods()) {

            if (Util.isPropertyMethod(m)) {
                if (Util.isItemProperty(m)) {
                    final Object object = m.invoke(item, new Object[0]);
                    if (object == null) {
                        String errorMessage = "In class "
                                + item.getClass().getSimpleName()
                                + " the method " + m.getName()
                                + " is not allowed to return null";
                        throw new NullPointerException(errorMessage);
                    }
                    if (object instanceof IArtifact) {
                        final IArtifact artifact = (IArtifact) object;
                        if (!Util.identityContains(referenced, artifact)) {
                            throw new IllegalStateException(
                                    item.getClass()
                                            + " references a object with "
                                            + NEWLINE
                                            + m.getName()
                                            + " which is NOT in its referencedArtifacts method.");
                        }
                        try {
                            Util.checkSubstitute(item, artifact);
                        } catch (final InstantiationException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    Util.check((IObject) object, checksession);
                }
            }
        }
    }

    private static void checkSubstitute(final IObject subject,
            final IArtifact artifact) throws InstantiationException,
            IllegalAccessException {
        // The fail msg must be computed here because of changes to the subject
        String msg = Util.getFailedSubstituionMsg(subject, artifact);
        final IArtifact toSubstituteWith = artifact.getClass()
                .newInstance();
        toSubstituteWith.setLogFile(artifact.getLogFile());
        toSubstituteWith.setName(artifact.getName());

        if (Util.identityContains(subject.referencedArtifacts(),
                toSubstituteWith)) {
            throw new IllegalStateException(
                    "Test failed due to assertion failure, not client code error");
        }

        subject.substitute(artifact, toSubstituteWith);

        if (toSubstituteWith == artifact) {
            System.err.println("ERRR");
        }
        if (Util.identityContains(subject.referencedArtifacts(), artifact)) {
            throw new IllegalStateException(msg);
        }
        if (!Util.identityContains(subject.referencedArtifacts(),
                toSubstituteWith)) {
            throw new IllegalStateException(msg);
        }

        // inverse checks
        subject.substitute(toSubstituteWith, artifact);
        if (!Util.identityContains(subject.referencedArtifacts(), artifact)) {
            throw new IllegalStateException(msg);
        }
        if (Util.identityContains(subject.referencedArtifacts(),
                toSubstituteWith)) {
            throw new IllegalStateException(msg);
        }

    }

    private static String getFailedSubstituionMsg(
            final IObject subject, final IArtifact failed) {
        List<Method> referers = Util.findMethodFor(subject, failed);
        StringBuilder msg = new StringBuilder(subject.getClass().getName()
                + NEWLINE + "does not substitute item" + NEWLINE + " > "
                + failed.getClass().getName() + " correctly.");

        msg.append(NEWLINE + "Possible referers:" + NEWLINE);
        for (Method method : referers) {
            msg.append(method.getName());
            msg.append(" in ");
            msg.append(method.getDeclaringClass().getName());
            msg.append(NEWLINE);
        }
        final String substituteError = msg.toString();
        return substituteError;
    }

    private static List<Method> findMethodFor(
            final IObject subject, final IArtifact artifact) {
        List<Method> methods = new ArrayList<Method>();
        for (Method method : subject.getClass().getMethods()) {
            if (Util.isPropertyMethod(method) && Util.isItemProperty(method)) {
                try {
                    Object object = method.invoke(subject, new Object[0]);
                    if (object == artifact) {
                        methods.add(method);
                    }
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return methods;
    }

    private static boolean identityContains(
            final Collection<IArtifact> haystack,
            final IArtifact needle) {
        for (final IArtifact straw : haystack) {
            if (straw == needle) {
                return true;
            }
        }
        return false;
    }

    private static boolean isItemProperty(final Method m) {
        return IObject.class.isAssignableFrom(m.getReturnType());
    }

    private static boolean isPropertyMethod(final Method m) {
        return !Util.objectMethods.contains(m)
                && m.getParameterTypes().length == 0;
    }
}
