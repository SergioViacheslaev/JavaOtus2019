package ru.otus.cachehw.cache.interfaces;

public interface HwListener<K, V> {
    void notify(K key, V value, String action);
}
