package com.zhaohuabing.demo;

import io.opentracing.Scope;
import io.opentracing.propagation.Format;
import io.opentracing.tag.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.opentracing.Tracer;
import io.opentracing.Span;
import org.springframework.web.client.RestTemplate;

/**
 * Huabing Zhao
 */
@RestController
public class EShopController {
    @Autowired private Tracer tracer;
    @Autowired private RestTemplate restTemplate;

    @RequestMapping(value = "/checkout")
    public String checkout(@RequestHeader HttpHeaders receivedHeaders) {
        Span span = tracer.buildSpan("checkout").withTag(Tags.SPAN_KIND.getKey(), Tags.SPAN_KIND_CLIENT).start();
        span.setBaggageItem("user", "mik");
        HttpHeaders sentHeaders = new HttpHeaders();
        tracer.inject(span.context(), Format.Builtin.HTTP_HEADERS, new HttpHeaderCarrier(sentHeaders));
        HttpEntity<String> entity = new HttpEntity<>("", sentHeaders);
        String order = restTemplate.exchange("http://inventory:8080/createOrder", HttpMethod.GET, entity, String.class).getBody();
        String payment = restTemplate.exchange("http://billing:8080/payment", HttpMethod.GET, entity, String.class).getBody();
        String delivery = restTemplate.exchange("http://delivery:8080/arrangeDelivery", HttpMethod.GET, entity, String.class).getBody();
        String result = "You have successfully checked out your shopping cart.\n";
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result + order + payment + delivery;
    }
}
