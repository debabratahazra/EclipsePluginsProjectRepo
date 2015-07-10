package com.zealcore.se.core.model;

public interface IStateTransition extends IArtifact {

    IState getFrom();

    IState getTo();

    void setFrom(final IState from);

    void setTo(final IState to);
}
