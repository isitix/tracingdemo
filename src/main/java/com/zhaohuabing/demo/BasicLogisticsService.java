package com.zhaohuabing.demo;

import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service public class BasicLogisticsService implements LogisticsService {
    @Autowired
    Tracer tracer;
    @Override
    public String transport(Span span) {
        Span childSpan = tracer.buildSpan("transport").asChildOf(span).start();
        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        childSpan.finish();
        return "shipping processed";
    }
}
