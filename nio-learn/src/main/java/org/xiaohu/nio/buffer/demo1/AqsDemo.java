package org.xiaohu.nio.buffer.demo1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class AqsDemo extends AbstractQueuedSynchronizer implements Lock {


    @Override
    protected boolean tryAcquire(int arg) {
        Thread t1 = Thread.currentThread();
        int stat = this.getState();
        if (stat == 0) {
            if (compareAndSetState(0, arg)) {
                setExclusiveOwnerThread(t1);
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean tryRelease(int arg) {
        Thread t1 = Thread.currentThread();
        if (t1 == this.getExclusiveOwnerThread()) {
            setState(0);
            return true;
        }
        return false;
    }


    static int cout = 0;

    public static void main(String[] args) throws InterruptedException {
        final AqsDemo aqsDemo = new AqsDemo();
        Thread[] t1 = new Thread[2000];
        for (int i = 0; i < 2000; i++) {
            t1[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        aqsDemo.lock();
                        cout++;
                    } finally {
                        aqsDemo.unlock();
                    }
                }
            });
        }
        for (int i = 0; i < 2000; i++) {
            t1[i].start();
            t1[i].join();
        }

        System.out.println("cout:" + cout);
    }

    @Override
    public void lock() {
        tryAcquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        release(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
