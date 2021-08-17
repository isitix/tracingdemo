package com.zhaohuabing.demo;

import io.opentracing.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.opentracing.Tracer;
import io.opentracing.Span;

/**
 * Huabing Zhao
 */
@RestController
public class EShopController {
    @Autowired private Tracer tracer;
    @Autowired private InventoryService inventoryService;
    @Autowired private BillingService billingService;
    @Autowired private DeliveryService deliveryService;

    @RequestMapping(value = "/checkout")
    public String checkout(@RequestHeader HttpHeaders headers) {
        Span span = tracer.buildSpan("checkout").start();
        try(Scope scope = tracer.scopeManager().activate(span)){
            inventoryService.createOrder();
            billingService.payment();
            deliveryService.arrangeDelivery();
            String result = "You have successfully checked out your shopping cart.";
            //Assume the checkout process takes 1 second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            span.finish();
        }
        return "successful checkout";
    }
}
