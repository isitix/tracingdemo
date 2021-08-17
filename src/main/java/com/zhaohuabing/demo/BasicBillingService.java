package com.zhaohuabing.demo;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service public class BasicBillingService implements BillingService{
    @Autowired
    Tracer tracer;
    @Override
    public String payment() {
        Span span = tracer.buildSpan("payment").start();
        try(Scope scope = tracer.scopeManager().activate(span)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            span.finish();
        }
        return "pay as you go";

    }
}
