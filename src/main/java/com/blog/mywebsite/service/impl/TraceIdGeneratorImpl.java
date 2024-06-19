package com.blog.mywebsite.service.impl;

import static com.blog.mywebsite.constant.LoggingConstant.X_TRACE_ID;

import com.blog.mywebsite.service.TraceIdGenerator;
import org.slf4j.MDC;

import java.util.UUID;

public class TraceIdGeneratorImpl implements TraceIdGenerator {
    @Override
    public void generateTraceId() {
        String traceId = UUID.randomUUID().toString();
        MDC.put(X_TRACE_ID, traceId);
    }
}
