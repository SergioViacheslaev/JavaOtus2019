package ru.otus.springmvcwebapp.cache.impl;

import org.springframework.stereotype.Component;
import ru.otus.springmvcwebapp.cache.interfaces.HwCache;
import ru.otus.springmvcwebapp.cache.interfaces.HwListener;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.*;

@Component
public class MyCache<K, V> implements HwCache<K, V>, HwListener<K, V> {
    private final static String ENTITY_CACHED = "Entity is cached.";
    private final static String ENTITY_LOADED = "Entity is loaded from cache.";
    private final static String ENTITY_REMOVED = "Entity is removed from cache.";

    private Map<K, V> cache = new WeakHashMap<>();
    //List of Weak links to HwListeners objects
    private List<WeakReference<HwListener<K, V>>> listeners = new ArrayList<>();
    //Queue is used to info about deleted by GC WeakReferences, for cleanUp method
    private ReferenceQueue<HwListener<K, V>> listenerReferenceQueue = new ReferenceQueue<>();


    @Override
    public void put(K key, V value) {
        cleanUp();
        cache.put(key, value);
        notify(key, value, ENTITY_CACHED);

        notifyAllListeners(key, value, ENTITY_CACHED);

    }

    @Override
    public void remove(K key) {
        cleanUp();
        V oldValue = cache.remove(key);
        notify(key, oldValue, ENTITY_REMOVED);

        notifyAllListeners(key, oldValue, ENTITY_REMOVED);

    }

    @Override
    public Optional<V> get(K key) {
        cleanUp();
        V value = cache.get(key);

        if (value != null) {
            notify(key, value, ENTITY_LOADED);

            notifyAllListeners(key, value, ENTITY_LOADED);

        }
        return Optional.ofNullable(value);
    }

    @Override
    public void addListener(HwListener listener) {
        listeners.add(new WeakReference<HwListener<K, V>>(listener, listenerReferenceQueue));
    }

    @Override
    public void removeListener(HwListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }


    //Remove deleted by GC Weakreferences from listeners list.
    private void cleanUp() {
        WeakReference listenerWeakRef = (WeakReference) listenerReferenceQueue.poll();

        while (listenerWeakRef != null) {
            listeners.remove(listenerWeakRef);
            listenerWeakRef = (WeakReference) listenerReferenceQueue.poll();
        }
    }

    private void notifyAllListeners(K key, V value, String action) {
        listeners.forEach(weakRefListener -> {
            HwListener<K, V> listener = weakRefListener.get();
            if (listener != null) {
                try {
                    listener.notify(key, value, action);
                } catch (Exception e) {
                    logger.error("Exception at listener", e);
                }
            }
        });
    }
}


