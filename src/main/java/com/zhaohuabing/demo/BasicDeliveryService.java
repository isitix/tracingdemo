package com.zhaohuabing.demo;

import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service public class BasicDeliveryService implements DeliveryService {
    @Autowired private LogisticsService logisticsService;
    @Autowired
    Tracer tracer;
    @Override
    public String arrangeDelivery(Span span) {
        Span childSpan = tracer.buildSpan("arrangeDelivery").asChildOf(span).start();
        logisticsService.transport(childSpan);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        childSpan.finish();
        return "delivery in progress";

    }
}
