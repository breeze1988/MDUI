package com.example;

/**
 * Created by Administrator on 2016/2/29.
 */
public class outputThread implements Runnable{
    private int num;
    private Object lock;

    @Override
    public void run() {
        try {
            while (true){
                synchronized (lock) {
                    lock.notifyAll();
                    lock.wait();
                    System.out.println(num);
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public outputThread(int num,Object lock) {
        super();
        this.num = num;
        this.lock = lock;
    }

    public static void main(String[] args){
        final Object lock = new Object();

        Thread thread1 = new Thread(new outputThread(1,lock));
        Thread thread2 = new Thread(new outputThread(2,lock));

        thread1.start();
        thread2.start();
    }
}
