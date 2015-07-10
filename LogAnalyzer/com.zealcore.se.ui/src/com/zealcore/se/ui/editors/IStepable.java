/*
 * 
 */
package com.zealcore.se.ui.editors;

// 
/**
 * The IStepable role indacates that it can be stepped forward or backward.
 */
public interface IStepable {

    /**
     * Step forward.
     */
    void stepForward();

    /**
     * Step back.
     */
    void stepBack();
}
