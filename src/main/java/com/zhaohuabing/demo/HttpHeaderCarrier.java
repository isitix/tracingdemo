package com.zhaohuabing.demo;

import org.springframework.http.HttpHeaders;

import java.util.Iterator;
import java.util.Map;

public class HttpHeaderCarrier implements io.opentracing.propagation.TextMap {
    private final HttpHeaders headers;

    public HttpHeaderCarrier(HttpHeaders headers) {
        this.headers = headers;
    }

    @Override
    public void put(String key, String value) {
        this.headers.set(key, value);
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return this.headers.toSingleValueMap().entrySet().iterator();
    }
}
