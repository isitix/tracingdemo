package com.zhaohuabing.demo;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service public class BasicLogisticsService implements LogisticsService {
    @Autowired
    Tracer tracer;
    @Override
    public String transport() {
        Span span = tracer.buildSpan("transport").start();
        try(Scope scope = tracer.scopeManager().activate(span)) {
            try {
                Thread.sleep(900);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
            span.finish();
        }
        return "shipping processed";
    }
}
