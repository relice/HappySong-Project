package com.example.relicemxd.happysong.Utils;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Package: org.xiu.activity.OrderList.view
 * @Author: Relice
 * @Date: 16/3/22
 * @Des: 遇见这样的高人怎么办？？
 * 下面是一个简单缓存的实现，相当牛叉！自己看吧，只有50行代码。
 */
public class EasyCache<K, V> {
    private final Lock lock = new ReentrantLock();
    private final int maxCapacity;
    private final Map<K, V> eden;
    private final Map<K, V> perm;

    public EasyCache(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.eden = new ConcurrentHashMap<K, V>(maxCapacity);
        this.perm = new WeakHashMap<K, V>(maxCapacity);
    }

    public V get(K k) {
        V v = this.eden.get(k);
        if (v == null) {
            lock.lock();
            try {
                v = this.perm.get(k);
            } finally {
                lock.unlock();
            }
            if (v != null) {
                this.eden.put(k, v);
            }
        }
        return v;
    }

    public void put(K k, V v) {
        if (this.eden.size() >= maxCapacity) {
            lock.lock();
            try {
                this.perm.putAll(this.eden);
            } finally {
                lock.unlock();
            }
            this.eden.clear();
        }
        this.eden.put(k, v);
    }
}


