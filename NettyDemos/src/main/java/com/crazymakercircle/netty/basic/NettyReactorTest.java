package com.crazymakercircle.netty.basic;

import com.crazymakercircle.im.common.bean.User;
import com.crazymakercircle.util.Logger;
import com.crazymakercircle.util.ThreadUtil;
import io.netty.channel.nio.NioEventLoopGroup;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class NettyReactorTest {

    @Test
    public void testJUCThreadPool() {

        ExecutorService pool = Executors.newFixedThreadPool(2);
        Runnable runTarget = new Runnable() {
            @Override
            public void run() {
                Logger.tcfo(" i am execute by  thread pool");
            }
        };

        for (int i = 0; i < 10; i++) {
            pool.submit(runTarget);

        }

        ThreadUtil.sleepSeconds(1000);
    }

    @Test
    public void testNettyThreadPool() {

//        ExecutorService pool = new DefaultEventLoop();
        ExecutorService pool = new NioEventLoopGroup(2);
        Runnable runTarget = new Runnable() {
            @Override
            public void run() {
                Logger.tcfo(" i am execute by  thread pool");
            }
        };
        for (int i = 0; i < 10; i++) {
            pool.submit(runTarget);

        }

        ThreadUtil.sleepSeconds(1000);
    }

    @Test
    public void testJUCscheduleAtFixedRate() {

        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
        Runnable runTarget = new Runnable() {
            @Override
            public void run() {
                Logger.tcfo(" i am execute by  thread pool");
            }
        };
        for (int i = 0; i < 10; i++) {
            pool.scheduleAtFixedRate(runTarget,1,1,TimeUnit.MINUTES);

        }

        ThreadUtil.sleepSeconds(1000);
    }
    @Test
    public void testNettyscheduleAtFixedRate() {

        ScheduledExecutorService pool = new NioEventLoopGroup(2);
        Runnable runTarget = new Runnable() {
            @Override
            public void run() {
                Logger.tcfo(" i am execute by  thread pool");
            }
        };
        for (int i = 0; i < 10; i++) {
            ((ScheduledExecutorService) pool).scheduleAtFixedRate(runTarget,10,10,TimeUnit.SECONDS);

        }

        ThreadUtil.sleepSeconds(1000);
    }

    @Test
    public void testUserbuilder() {
        User user = User.builder()
                .devId("卷王01")
                .name("疯狂创客圈 卷王")
                .platform(17)
                .build();
        System.out.println(user);
    }
}