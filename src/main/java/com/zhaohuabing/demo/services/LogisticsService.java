package com.zhaohuabing.demo.services;

import com.zhaohuabing.demo.HttpHeaderCarrier;
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
public class LogisticsService {
    @Autowired
    Tracer tracer;

    @RequestMapping(value = "/transport")
    public String transport(@RequestHeader HttpHeaders receivedHeaders) {
        SpanContext spanContext = tracer.extract(Format.Builtin.HTTP_HEADERS, new HttpHeaderCarrier(receivedHeaders));
        Span span = tracer.buildSpan("transport").asChildOf(spanContext).start();
        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String user = span.getBaggageItem("user");
        span.finish();
        return String.format("shipping processed baggage user %s\n", user);
    }
}
