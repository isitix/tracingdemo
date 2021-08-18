package com.zhaohuabing.demo.services;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryService  {
    @Autowired
    Tracer tracer;
    @RequestMapping(value = "/createOrder")
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
        return "Order created\n";
    }
}
