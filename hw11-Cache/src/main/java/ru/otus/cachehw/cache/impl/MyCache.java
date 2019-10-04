package ru.otus.cachehw.cache.impl;

import ru.otus.cachehw.cache.interfaces.HwCache;
import ru.otus.cachehw.cache.interfaces.HwListener;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;


public class MyCache<K, V> implements HwCache<K, V> {
    private Map<K, V> cache = new WeakHashMap<>();

    //List of Weak links to HwListeners objects
    private List<WeakReference<HwListener<K, V>>> listeners = new ArrayList<>();
    //Queue is used to info about deleted by GC WeakReferences, for cleanUp method
    private ReferenceQueue<HwListener<K, V>> listenerReferenceQueue = new ReferenceQueue<>();


    @Override
    public void put(K key, V value) {
        cleanUp();
        cache.put(key, value);
        listeners.forEach((listener -> listener.get().notify(key, value, "Entity is cached.")));
    }

    @Override
    public void remove(K key) {
        cleanUp();
        V oldValue = cache.remove(key);
        listeners.forEach((listener -> listener.get().notify(key, oldValue, "Entity is removed from cache.")));
    }

    @Override
    public V get(K key) {
        cleanUp();
        V value = cache.get(key);
        listeners.forEach((listener -> listener.get().notify(key, value, "Entity is loaded from Cache")));
        return value;
    }

    @Override
    public void addListener(HwListener listener) {
        listeners.add(new WeakReference<HwListener<K, V>>(listener, listenerReferenceQueue));
    }

    @Override
    public void removeListener(HwListener listener) {
        listeners.remove(listener);
    }

    @Override
    public boolean isValueCached(K key) {
        return cache.containsKey(key);
    }

    //Remove deleted by GC Weakreferences from listeners list.
    private void cleanUp() {
        WeakReference listenerWeakRef = (WeakReference) listenerReferenceQueue.poll();

        while (listenerWeakRef != null) {
            listeners.remove(listenerWeakRef);
            listenerWeakRef = (WeakReference) listenerReferenceQueue.poll();
        }
    }


}
