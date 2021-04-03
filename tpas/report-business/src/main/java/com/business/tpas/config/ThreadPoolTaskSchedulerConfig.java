package com.business.tpas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author dude
 * @version 1.0
 * @classname ThreadPoolTaskSchedulerConfig
 * @description 线程池配置
 * @date 2021/4/3
 **/
@Configuration
public class ThreadPoolTaskSchedulerConfig {

    @Bean(name = "commonThreadPool")
    public ThreadPoolExecutor commonThreadPool() {
        return new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(),
                60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1024),
                Executors.defaultThreadFactory(), new AssessTaskRejectedExecutionHandler());
    }

    /**
     * 队列超限的拒绝策略
     *
     * @author dude
     * @date 2021/4/3
     **/
    private static class AssessTaskRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            new Thread(r, "commonThreadPool-extra-thread-%d" + new Random().nextInt(10)).start();
        }
    }

}
