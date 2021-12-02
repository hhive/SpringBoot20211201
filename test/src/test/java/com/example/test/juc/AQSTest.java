package com.example.test.juc;

import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO
 *
 * @author lih@yunrong.cn
 * @version V3.0
 * @since 2021/11/30 17:16
 */
public class AQSTest {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.lock();
        System.out.println(lock);
        lock.unlock();
    }
}
