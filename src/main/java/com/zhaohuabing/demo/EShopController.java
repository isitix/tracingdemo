package com.zhaohuabing.demo;

import io.opentracing.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
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

    @RequestMapping(value = "/checkout")
    public String checkout(@RequestHeader HttpHeaders headers) {
        Span span = tracer.buildSpan("checkout").start();
        String order, payment, delivery, result;
        try(Scope scope = tracer.scopeManager().activate(span)){
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(3000);
            factory.setReadTimeout(3000);
            RestTemplate restTemplate = new RestTemplate(factory);
            order = restTemplate.getForObject("http://inventory:8080/createOrder", String.class);
            payment = restTemplate.getForObject("http://billing:8080/payment", String.class);
            delivery = restTemplate.getForObject("http://delivery:8080/arrangeDelivery", String.class);
            result = "You have successfully checked out your shopping cart.\n";
            //Assume the checkout process takes 1 second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            span.finish();
        }
        return result + order + payment + delivery;
    }
}
