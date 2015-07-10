/**
 * 
 */
package com.zealcore.se.iw;

final class MemoryRegistry extends GenericImportRegistry {

    MemoryRegistry() {
        try {} catch (final NullPointerException ex) {
            ex.printStackTrace();

        }
    }

    @Override
    public void restore() {}

    @Override
    public void save() {}
}
