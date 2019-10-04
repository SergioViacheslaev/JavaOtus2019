package ru.otus.cachehw.cache.interfaces;

public interface HwCache<K, V> {

    void put(K key, V value);

    void remove(K key);

    V get(K key);

    void addListener(HwListener listener);

    void removeListener(HwListener listener);

    boolean isValueCached(K key);


}
