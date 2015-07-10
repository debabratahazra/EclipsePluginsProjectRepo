package com.zealcore.srl.offline;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class Internalizer implements ITransformer {

    private final File externalResource;

    private final List<IResolver> resolver = new ArrayList<IResolver>();

    public Internalizer(final File externalResource) {
        this.externalResource = externalResource;
        resolver.add(new StringResolver());
        resolver.add(new SignalResolver());
    }

    public void transform(final Blackbox blackbox) {
        blackbox.setMagicPhys(ResolveUtil.getMagicPhys(externalResource));
        for (final TypedCircularMessage message : blackbox.getTypedCircularMessages()) {
            // foreach resolver call resolve on each message
            for (final IResolver r : resolver) {
                r.resolve(blackbox, message, externalResource);
            }
        }
        for (final TypedLinearMessage message : blackbox.getTypedLinearMessages()) {
            // foreach resolver call resolve on each message
            for (final IResolver r : resolver) {
                r.resolve(blackbox, message, externalResource);
            }
        }
    }
}
