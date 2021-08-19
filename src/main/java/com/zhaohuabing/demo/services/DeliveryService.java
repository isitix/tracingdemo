package com.zhaohuabing.demo.services;

import com.zhaohuabing.demo.HttpHeaderCarrier;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DeliveryService {
    @Autowired private LogisticsService logisticsService;
    @Autowired
    Tracer tracer;
    @Autowired private RestTemplate restTemplate;

    @RequestMapping(value = "/arrangeDelivery")
    public String arrangeDelivery(@RequestHeader HttpHeaders receivedHeaders) {
        SpanContext spanContext = tracer.extract(Format.Builtin.HTTP_HEADERS, new HttpHeaderCarrier(receivedHeaders));
        Span span = tracer.buildSpan("arrangeDelivery").asChildOf(spanContext).start();
        HttpHeaders sentHeaders = new HttpHeaders();
        tracer.inject(span.context(), Format.Builtin.HTTP_HEADERS, new HttpHeaderCarrier(sentHeaders));
        HttpEntity<String> entity = new HttpEntity<>("", sentHeaders);
        String transport = restTemplate.exchange("http://logistics:8080/transport", HttpMethod.GET, entity, String.class).getBody();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        span.finish();
        return "delivery in progress\n" + transport;

    }
}
