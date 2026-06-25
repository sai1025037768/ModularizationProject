package com.travel.biz.service.base;

/**
 * Represents a cancelable business service call.
 */
public interface Cancelable {

    void cancel();

    boolean isCanceled();
}
