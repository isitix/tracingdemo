package com.zhaohuabing.demo;

import io.jaegertracing.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.opentracing.Tracer;
import io.opentracing.Span;

import static io.jaegertracing.Configuration.JAEGER_SERVICE_NAME;
import static java.lang.System.getProperty;

/**
 * Huabing Zhao
 */
@RestController
public class EShopController {
    @Autowired
    private Tracer tracer;
    @Autowired private InventoryService inventoryService;
    @Autowired private BillingService billingService;
    @Autowired private DeliveryService deliveryService;

    private static final Logger LOG = LoggerFactory.getLogger(EShopController.class);

    @RequestMapping(value = "/checkout")
    public String checkout(@RequestHeader HttpHeaders headers) {
        Span span = tracer.buildSpan("checkout").start();
        inventoryService.createOrder(span);
        billingService.payment(span);
        deliveryService.arrangeDelivery(span);
        String result = "You have successfully checked out your shopping cart.";
        //Assume the checkout process takes 1 second
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        span.finish();
        return result;
    }
}
