package io.github.kahar.executor.thread.pool;

import java.util.concurrent.*;

public class Demo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

//        ThreadPoolExecutor executor =
//                (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });

        System.out.println("executor.getPoolSize()=" + executor.getPoolSize());
        System.out.println("executor.getQueue().size()=" + executor.getQueue().size());

        Future<String> future =
                executor.submit(() -> {
                    Thread.sleep(5000);
                    return "HAKUNA MATATA";
                });

        System.out.println("future result:" + future.get());

        executor.shutdown();

        // This approach allow to add minimal pool size, maximum pool size and allow to kill thread if is idle longer than 60 seconds and
        // there are more threads than core pool size
        executor = new ThreadPoolExecutor(10, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>());
    }
}
