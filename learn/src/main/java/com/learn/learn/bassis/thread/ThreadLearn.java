package com.learn.learn.bassis.thread;

/**
 * @Author: LL
 * @Description: Thread的学习
 * @Date:Create：in 2021/3/4 11:10
 */
class ThreadLearn {

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new MyThread());
        Thread thread2 = new Thread(new MyThread());
        thread1.setName("线程1");
        thread2.setName("线程2");
        thread1.start();
        thread2.start();
        //阻塞当前调用它的线程，等待join执行完毕，当前线程继续执行。
        //join和sleep会使线程进入blocked状态，等join，中断就会回到Runable状态（等待cpu调度）
        thread2.join();
        thread1.sleep(1000);
        //线程把操作暂时给其他线程执行,线程会从Running变成Runnable
        thread1.yield();
        //让线程进入等待状态，wait()表示无限等待，除非被notify和notifyAll唤醒
        //和sleep的区别：sleep释放cpu的执行权，但不释放同步锁，wait是都释放
        thread2.wait();
        thread2.notifyAll();
        //中断线程 interrupt()的功能就是让线程再次获得执行权限，打断wait、join()、sleep()等方法
        //interrupt()并不是真正的让线程停下来，它其实只是改变了一下执行标记，最终还是需要我们来完成线程的停止
        thread1.interrupt();
    }

    static class MyThread implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                //判断线程是否中断
                if (Thread.currentThread().isInterrupted()) {

                }
                System.out.println(Thread.currentThread().getName() + "运行 i =" + i);
            }
        }
    }
}
