package com.house.framework.config.tenant;

import java.util.concurrent.ConcurrentHashMap;

public final class TenantActionContext {

    private static final ThreadLocal<ConcurrentHashMap<String, Integer>> threadLocal = ThreadLocal.withInitial(ConcurrentHashMap::new);

    private static class ContextHolder {
        private final static TenantActionContext tenantActionContext = new TenantActionContext();
    }

    public static TenantActionContext getTenantActionContext() {
        return ContextHolder.tenantActionContext;
    }

    public ConcurrentHashMap getTenantContext() {
        return threadLocal.get();
    }

    public void setTenantContext(String key, Integer tenantId) {
        threadLocal.get().put(key, tenantId);
    }
}
