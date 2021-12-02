package com.example.test.jvm;

/**
 * TODO
 *
 * @author lih@yunrong.cn
 * @version V3.0
 * @since 2021/11/16 10:25
 */
public class TestClass {
    private int m;
    private Long n;
    // public Long inc() {
    //     return m + 1 + n;
    // }

    public int inc() {
        int x;
        try {
            x = 1;
            return x;
        } catch (Exception e) {
            x = 2;
            return x;
        } finally {
            x = 3;
        }
    }

}
