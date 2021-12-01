/*
 * yunrong.cn Inc. Copyright (c) 2014-2020 All Rights Reserved
 */

package com.example.test;

import java.time.LocalDate;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.web.WebApplication;
import lombok.extern.slf4j.Slf4j;

/**
 * 抽象基础Test
 *
 * @author yubei@yunrong.cn
 * @version V3.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public abstract class AbstractBaseTest {

    @LocalServerPort
    private int port;


    /** 测试业务日期(yyyy-mm-dd) */
    protected String transDate = LocalDate.now()
        .toString();

    /**
     * 初始化
     */
    @Before
    public void init() {
        this.transDate = StringUtils.isBlank(transDate) ? this.transDate : transDate;
        System.out.println(transDate);
    }


    /**
     * 获取访问url
     *
     * @param path
     * @return
     */
    public String getUrl(String path) {
        return String.format("http://localhost:%d" + path, port);
    }

}
