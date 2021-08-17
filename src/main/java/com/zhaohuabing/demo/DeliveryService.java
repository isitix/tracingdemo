package com.zhaohuabing.demo;

import io.opentracing.Span;

public interface DeliveryService {
    public String arrangeDelivery(Span span);
}
