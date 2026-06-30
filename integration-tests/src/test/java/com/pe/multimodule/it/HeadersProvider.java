package com.pe.multimodule.it;

import com.pe.multimodule.api.rest.HeaderConstants;
import org.apache.http.HttpHeaders;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class HeadersProvider implements Supplier<Map<String, ?>> {

    private String requesterId;

    @Override
    public Map<String, ?> get() {
        return getHttpHeaders();
    }

    public Map<String, ?> getHttpHeaders() {
        // The id might be different on next test case, this is why we always create
        final var httpHeaders = new HashMap<String, Object>();
        httpHeaders.put("Cookie", "locale=en_US");
        httpHeaders.put("locale", "en_US");

        // We assume the Gateway done its job to authenticate the user and set the requester id header
        httpHeaders.put(HeaderConstants.REQUESTER_ID, requesterId);

        httpHeaders.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        httpHeaders.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    public void setRequesterHeader(UUID requesterId) {
        if (requesterId != null) {
            this.requesterId = requesterId.toString();
        } else {
            this.requesterId = null;
        }
    }

    public void clearCookies() {
        this.requesterId = null;
    }
}
