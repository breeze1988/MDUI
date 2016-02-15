package util;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by breeze on 2016/2/2.
 */
public class ThreadPool {
    ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
    ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
    ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();  //用于数据库操作

}
