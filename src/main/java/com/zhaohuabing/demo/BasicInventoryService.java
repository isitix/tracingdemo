package com.zhaohuabing.demo;

import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service public class BasicInventoryService implements InventoryService {
    @Autowired
    Tracer tracer;
    @Override
    public String createOrder(Span span) {
        Span childSpan = tracer.buildSpan("createOrder").asChildOf(span).start();
        try {
            Thread.sleep(750);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        childSpan.finish();
        return "Order created";
    }
}
