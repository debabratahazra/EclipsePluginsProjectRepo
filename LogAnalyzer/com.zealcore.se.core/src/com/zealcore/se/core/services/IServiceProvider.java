package com.zealcore.se.core.services;

public interface IServiceProvider {
    <T> T getService(final Class<T> serviceType);

    <T> void registerService(final Class<T> type, T instance);
}
