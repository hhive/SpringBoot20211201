package com.example.test.juc;

import lombok.Synchronized;

/**
 * TODO
 *
 * @author lih@yunrong.cn
 * @version V3.0
 * @since 2021/11/30 15:38
 */
public class VolatileTest {

    private static volatile VolatileTest instance = null;

    //单例模式中应用volatile，确保指令不会重排序
    public VolatileTest getInstance() {
        if (instance == null) {
            synchronized (VolatileTest.class) {
                if (instance == null) {
                    instance = new VolatileTest();
                }
            }
        }
        return instance;
    }

    int a;
    volatile int v1 = 1;
    volatile int v2 = 2;

    void readAndWrite() {
        int i = v1; // 第一个 volatile 读
        int j = v2; // 第二个 volatile 读
        a = i + j; // 普通写
        v1 = i + 1; // 第一个 volatile 写
        v2 = j * 2; // 第二个 volatile 写
    }



}

