package com.zhaohuabing.demo;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service public class BasicDeliveryService implements DeliveryService {
    @Autowired private LogisticsService logisticsService;
    @Autowired
    Tracer tracer;
    @Override
    public String arrangeDelivery() {
        Span span = tracer.buildSpan("arrangeDelivery").start();
        try(Scope scope = tracer.scopeManager().activate(span)) {
            logisticsService.transport();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            span.finish();
        }
        return "delivery in progress";

    }
}
