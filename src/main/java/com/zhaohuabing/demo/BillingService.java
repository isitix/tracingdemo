package com.zhaohuabing.demo;

import io.opentracing.Span;

public interface BillingService {
    public String payment(Span span);
}