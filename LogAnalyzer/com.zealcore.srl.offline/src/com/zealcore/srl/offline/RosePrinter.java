package com.zealcore.srl.offline;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class RosePrinter implements IPrinter {

    // private static final String PORT_INDEX_STR = "portIndex";

    // private static final String PORT_STR = "port";

    // private static final String SIGNAL_DESCRIPTORS = "signalDescriptors";

    // private static final String STATE = "state";

    // private static final String ACTOR_INSTANCE = "actorInstance";

    // private static final String ACTOR = "actor";

    // private static final String SEPARATOR = ",";

    private PrintStream out;

    // private Map<Long, String> tasks;

    // private Map<Object, Long> missing = new LinkedHashMap<Object, Long>();

    private HashSet<String> typeDefs;

    public RosePrinter() {
    }

    public void transform(final Blackbox blackbox) {

        typeDefs = new HashSet<String>();
        for (final TypedLinearMessage message : blackbox.getTypedLinearMessages()) {
            if (!typeDefs.contains(message.getStruct().getName())) {
                writeStruct(message);
            }
        }

        for (final TypedLinearMessage message : blackbox.getTypedLinearMessages()) {
            Logger.log(message);
        }

        write("#ZealCore-ModelReplay:ROSE-RT:1.0");
        final Collection<TypedCircularMessage> msgs = blackbox
                .getTypedCircularMessages();

        final List<TypedCircularMessage> cmsgs = new ArrayList<TypedCircularMessage>(
                msgs);
        sort(cmsgs);

        for (final TypedCircularMessage message : cmsgs) {
            if (!typeDefs.contains(message.getStruct().getName())) {
                writeStruct(message);
            }
        }
        write("");
    }

    private void writeStruct(final AbstractTypedMessage message) {
        typeDefs.add(message.getStruct().getName());
        final Struct struct = message.getStruct();
        final StringBuilder bld = new StringBuilder();
        bld.append("type ").append(struct.getName());
        for (final Field field : struct.getFields()) {
            bld.append(field.getName()).append(":").append(
                    field.getType().getName()).append("\t");
        }
        write(bld.toString());
    }

    private void write(final String string) {
        out.println(string);
    }

    private void sort(final List<TypedCircularMessage> cmsgs) {
        Collections.sort(cmsgs, new Comparator<TypedCircularMessage>() {

            public int compare(final TypedCircularMessage o1,
                    final TypedCircularMessage o2) {
                final long ts1 = ((CircularMessage) o1.getMessage()).getTs();
                final long ts2 = ((CircularMessage) o2.getMessage()).getTs();
                return Long.valueOf(ts1).compareTo(ts2);
            }

        });
    }

    public void setOut(final PrintStream out) {
        this.out = out;
    }

}
