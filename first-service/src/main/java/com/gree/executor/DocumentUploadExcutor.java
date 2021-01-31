package com.gree.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author yangLongFei 2021-01-25-16:07
 */
public class DocumentUploadExcutor {

    //核心线程数
    private static final int CORE_SIZE = 3;
    //最大线程数
    private static final int MAX_SIZE = 10;
    //连接时间
    private static final long KEEP_ALIVE_TIME = 60L;
    //阻塞队列的大小为20
    private static ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(20);

    private static ThreadPoolExecutor executor = null;

    public DocumentUploadExcutor() {
        // 单例模式双重验证
        if (executor == null) {
            synchronized (DocumentUploadExcutor.class) {
                if (executor == null) {
                    executor = new ThreadPoolExecutor(
                            CORE_SIZE,
                            MAX_SIZE,
                            KEEP_ALIVE_TIME,
                            TimeUnit.SECONDS,
                            queue,
                            new ThreadFactoryBuilder().setNameFormat("document_file_%d").build(), //线程名称
                            new ThreadPoolExecutor.CallerRunsPolicy());
                }
            }
        }
    }

    public void put(Runnable runnable) {
        executor.execute(runnable);
    }

}
