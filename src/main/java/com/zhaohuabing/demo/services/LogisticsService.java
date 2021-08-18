package com.zhaohuabing.demo.services;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogisticsService {
    @Autowired
    Tracer tracer;
    @RequestMapping(value = "/transport")
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
        return "shipping processed\n";
    }
}
