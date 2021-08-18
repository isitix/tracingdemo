package com.zhaohuabing.demo.services;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DeliveryService {
    @Autowired private LogisticsService logisticsService;
    @Autowired
    Tracer tracer;
    @RequestMapping(value = "/arrangeDelivery")
    public String arrangeDelivery() {
        Span span = tracer.buildSpan("arrangeDelivery").start();
        String transport;
        try(Scope scope = tracer.scopeManager().activate(span)) {
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(3000);
            factory.setReadTimeout(3000);
            RestTemplate restTemplate = new RestTemplate(factory);
            transport = restTemplate.getForObject("http://logistics:8080/transport", String.class);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            span.finish();
        }
        return "delivery in progress\n" + transport;

    }
}
