package com.example.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

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

    @Value("${local.server.port}")
    private String port;

    @Test
    public void test1() {
        System.out.println(111);
    }
}
