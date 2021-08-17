package com.zhaohuabing.demo;

import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service public class BasicBillingService implements BillingService{
    @Autowired
    Tracer tracer;
    @Override
    public String payment(Span span) {
        Span childSpan = tracer.buildSpan("payment").asChildOf(span).start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        childSpan.finish();
        return "pay as you go";

    }
}
