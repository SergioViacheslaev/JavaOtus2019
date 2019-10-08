package ru.otus.cachehw.cache.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface HwListener<K, V> {
    Logger logger = LoggerFactory.getLogger(HwListener.class);

    default void notify(K key, V value, String action) {
        logger.info("[Default Listener] key:{}, value:{}, action: {}", key, value, action);
    }
}
