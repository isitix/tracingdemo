package com.zhaohuabing.demo;

import io.opentracing.Span;

public interface LogisticsService {
    public String transport(Span span);
}
