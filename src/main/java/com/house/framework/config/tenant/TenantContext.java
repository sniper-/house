package com.house.framework.config.tenant;

import com.google.common.collect.Maps;

import java.util.Map;

public class TenantContext {

    private static final Map<String, Object> contextMap = Maps.newConcurrentMap();

    public void putTenantIdPair(String key, Integer tenantId) {
        contextMap.put(key, tenantId);
    }

    public Integer getTenantIdWithKey(String key) {
        return (Integer) contextMap.get(key);
    }
}
