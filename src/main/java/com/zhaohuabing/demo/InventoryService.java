package com.zhaohuabing.demo;

import io.opentracing.Span;

/**
 * Huabing Zhao
 */

public interface InventoryService {
    public String createOrder(Span span);
}
