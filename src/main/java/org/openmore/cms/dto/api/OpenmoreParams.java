package org.openmore.cms.dto.api;

/**
 * 自定义key-value对象
 */
public class OpenmoreParams<T, V> {
    T key;
    V value;

    public T getKey() {
        return key;
    }

    public OpenmoreParams setKey(T key) {
        this.key = key;
        return this;
    }

    public V getValue() {
        return value;
    }

    public OpenmoreParams setValue(V value) {
        this.value = value;
        return this;
    }
}