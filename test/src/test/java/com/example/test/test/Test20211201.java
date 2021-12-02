package com.example.test.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.example.test.AbstractBaseTest;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author lih@yunrong.cn
 * @version V3.0
 * @since 2021/12/1 14:18
 */
@Slf4j
public class Test20211201 extends AbstractBaseTest {

    public static String string = "static";

    public Test20211201() {
        System.out.println("Test20211201");
    }
    // @Value("${local.server.port}")
    // private String port;

    // @Test
    public void test1() {
        // System.out.println(port);
        System.out.println(111);
    }
}
