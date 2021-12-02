package com.example.test.model;

import lombok.Data;

/**
 * @author lih@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020/3/10 10:45
 */
@Data
public class Man extends Person{
    private Integer size;

    public Man(Integer size) {
        this.size = size;
    }
}
