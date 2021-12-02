package com.example.test.juc;

/**
 * TODO
 *
 * @author lih@yunrong.cn
 * @version V3.0
 * @since 2021/12/1 21:26
 */
public class ThreadLocalTest {
    //ThreadLocal可以只使用一个变量而在每一个线程保存一份线程独有的数据
    private static final ThreadLocal<String> sThreadLocal = new ThreadLocal<>();
    //InheritableThreadLocal可获取线程创建之前主线程设置的数据,但注意，一个ThreadLocal在每个线程都只能
    //保存一个值，因此如果子线程又调用了set，会覆盖主线程的值
    public static ThreadLocal<String> iThreadLocal = new InheritableThreadLocal<String>();

    private void showThreadLocal() {
        sThreadLocal.set("showThreadLocal: 这是在主线程中");
        System.out.println("线程名字：" + Thread.currentThread()
            .getName() + "---" + sThreadLocal.get());
        //线程a
        new Thread(new Runnable() {
            @Override
            public void run() {
                sThreadLocal.set("这是在线程a中");
                System.out.println("线程名字：" + Thread.currentThread()
                    .getName() + "---" + sThreadLocal.get());
            }
        }, "线程a").start();
    }

    private void showInheritableThreadLocal() {
        iThreadLocal.set("showInheritableThreadLocal: 这是在主线程中");
        System.out.println("线程名字：" + Thread.currentThread()
            .getName() + "---" + iThreadLocal.get());
        //线程b
        new Thread(new Runnable() {
            @Override
            public void run() {
                // iThreadLocal.set("这是在线程b中");
                System.out.println("线程名字：" + Thread.currentThread()
                    .getName() + "---" + iThreadLocal.get());
            }
        }, "线程b").start();
        //线程c，如果子线程重新设值，会替代主线程的值，因为存入map的key是同一个
        new Thread(() -> {
            iThreadLocal.set("这是在线程c中");
            System.out.println("线程名字：" + Thread.currentThread()
                .getName() + "---" + iThreadLocal.get());
        }, "线程c").start();
    }
    public static void main(String[] args) {
        ThreadLocalTest threadLocalTest = new ThreadLocalTest();
        threadLocalTest.showThreadLocal();
        threadLocalTest.showInheritableThreadLocal();

    }
}