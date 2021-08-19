package com.zhaohuabing.demo.services;

import com.zhaohuabing.demo.HttpHeaderCarrier;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingService  {
    @Autowired
    Tracer tracer;
    @RequestMapping(value = "/payment")
    public String payment(@RequestHeader HttpHeaders receivedHeaders) {
        SpanContext spanContext = tracer.extract(Format.Builtin.HTTP_HEADERS, new HttpHeaderCarrier(receivedHeaders));
        Span span = tracer.buildSpan("payment").asChildOf(spanContext).start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        span.finish();
        return "pay as you go\n";
    }
}
