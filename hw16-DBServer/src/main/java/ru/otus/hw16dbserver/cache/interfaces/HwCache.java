package ru.otus.hw16dbserver.cache.interfaces;

import java.util.Optional;

public interface HwCache<K, V> {

    void put(K key, V value);

    void remove(K key);

    Optional<V> get(K key);

    void addListener(HwListener listener);

    void removeListener(HwListener listener);

}
