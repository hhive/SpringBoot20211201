package com.example.test.jvm;

import com.example.test.model.Man;

/**
 * TODO
 *
 * @author lih@yunrong.cn
 * @version V3.0
 * @since 2021/11/15 17:16
 */
public class Test20211115 extends Man {

    public Test20211115(Integer size) {
        super(size);
    }

    public static void main(String[] args) {
         Test20211115 test20211115 = new Test20211115(1);
        Man man = new Man(1);
        // System.out.println(1);
    }
}
