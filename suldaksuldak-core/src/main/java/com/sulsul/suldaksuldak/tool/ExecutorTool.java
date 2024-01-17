package com.sulsul.suldaksuldak.tool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorTool {
    private static final int maxCore = Runtime.getRuntime().availableProcessors();

    public static ExecutorService getExecutor() {
        ThreadPoolExecutor threadPool =
                new ThreadPoolExecutor(
                        maxCore / 2,
                        maxCore,
                        30,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(Integer.MAX_VALUE)
                );

        threadPool.setRejectedExecutionHandler(
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        return threadPool;
    }
}
