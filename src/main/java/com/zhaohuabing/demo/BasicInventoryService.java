package com.zhaohuabing.demo;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service public class BasicInventoryService implements InventoryService {
    @Autowired
    Tracer tracer;
    @Override
    public String createOrder() {
        Span span = tracer.buildSpan("createOrder").start();
        try(Scope scope = tracer.scopeManager().activate(span)) {
            try {
                Thread.sleep(750);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            span.finish();
        }
        return "Order created";
    }
}
