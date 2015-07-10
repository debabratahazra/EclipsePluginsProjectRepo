package com.zealcore.se.ui.editors;

public interface IRuler {

    int toScreen(long x);
    
    long toTimestamp(int x);

    int min();

    int max();
}
