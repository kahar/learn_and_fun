package io.github.kahar.framework;


import io.github.kahar.framework.annotation.Cacheable;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class CacheableHandler extends AbstractProxyHandler {

    private final Map<List<Object>, Object> cacheContainers = new ConcurrentHashMap<>();

    public CacheableHandler(final Object objectToHandle) {
        super(objectToHandle, Cacheable.class);
    }

    public List<Object> createKeyCache(final Method method, final Object[] args) {
        return List.of(method, Arrays.asList(args));
    }

    public Object takeResultOrCalculate(final Method method, Object[] args, final Supplier<Object> resultSupplier) {
        final List<Object> keyCache = createKeyCache(method, args);
        return cacheContainers.computeIfAbsent(keyCache, key -> resultSupplier.get());
    }

}
