package com.wind.androidlearn.bassis.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: LL
 * @Description:线程池的使用学习
 * @Date:Create：in 2021/1/8 11:07
 */
class ThreadPoolLearn {
    static ExecutorService executorService = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
    }
    /**
     * 线程池一系列创建都由ThreadPoolExecutor完成,构造方法分别为
     * corePoolSize 核心线程数，除非设置核心线程超时(allowCoreThreadTimeOut)，线程一直存活在线程池中，即使线程处于空闲状态。
     * maximumPoolSize 线程池中允许存在的最大线程数。
     * workQueue 工作队列，当核心线程都处于繁忙状态时，将任务提交到工作队列中。如果工作队列也超过了容量，会去尝试创建一个非核心线程执行任务。
     * keepAliveTime 非核心线程处理空闲状态的最长时间，超过该值线程则会被回收。
     * TimeUnit 时间格式，是分钟还是秒 TimeUnit.MILLISECONDS
     * threadFactory 线程工厂类，用于创建线程。
     * RejectedExecutionHandler 工作队列饱和策略，比如丢弃、抛出异常等。
     *
     * workQueue ==>
     *      LinkedBlockingQueue 内部用链表实现的阻塞队列，默认的构造函数使用Integer.MAX_VALUE作为容量，即常说的“无界”，另可以通过带capacity参数的构造函数限制容量。使用Executors工具类创建的线程池容量均为无界的。
     *      SynchronousQueue 容量为0，每当有任务添加进来时会立即触发消费，即每次插入操作一定伴随一个移除操作，反之亦然。
     *      DelayedWorkQueue 用数组实现，默认容量为16，支持动态扩容，可对延迟任务进行排序，类似优先级队列，搭配ScheduledThreadPoolExecutor可完成定时或延迟任务。
     *      ArrayBlockingQueue 它不在上述线程池的体系当中，它基于数组实现，容量固定且不可扩容。
     *
     * RejectedExecutionHandler ==>
     *      DiscardPolicy 将丢弃被拒绝的任务。
     *      DiscardOldestPolicy 将丢弃队列头部的任务，即先入队的任务会出队以腾出空间。
     *      AbortPolicy	抛出RejectedExecutionException异常。
     *      CallerRunsPolicy 在execute方法的调用线程中运行被拒绝的任务。
     */
}
