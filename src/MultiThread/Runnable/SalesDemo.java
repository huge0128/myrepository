package MultiThread.Runnable;

import MultiThread.Util.ThreadUtil;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class SalesDemo {
    public static final int MAX_AMOUNT = 5;

    static class StoreGoods extends Thread {
        StoreGoods(String name) {
            super(name);
        }

        private int goodsAmount = MAX_AMOUNT;

        public void run() {
            for (int i = 0; i < MAX_AMOUNT; i++) {
                if (this.goodsAmount > 0) {
                    System.out.println(ThreadUtil.getCurThreadName() + " sold one, less:"
                    + (--goodsAmount));
                    //sleep(10);
                }
            }
            System.out.println(ThreadUtil.getCurThreadName() + " end");
        }
    }

    static class MallGoods implements Runnable {
        private AtomicInteger goodsAmount = new AtomicInteger(MAX_AMOUNT);

        @Override
        public void run() {
            for (int i = 0; i <= MAX_AMOUNT; i++) {
                if (this.goodsAmount.get() > 0) {
                    System.out.println(ThreadUtil.getCurThreadName() + " sold one, less" +
                            (goodsAmount.decrementAndGet()));
                    //sleep(10);
                }
            }
            System.out.println(ThreadUtil.getCurThreadName() + " end");
        }
    }

    public static void main(String[] args) throws InterruptedException{
        System.out.println("version 1");
        for (int i = 1; i <= 2 ; i++) {
            Thread thread = null;
            thread =new StoreGoods("seller-" + i);
            thread.start();
        }

        Thread.sleep(1000);

        System.out.println("version 2");
        MallGoods mallGoods = new MallGoods();
        for (int i = 1; i <= 2 ; i++) {
            Thread thread = null;
            thread  =new Thread(mallGoods, "seller-" + i);
            thread.start();
        }
        System.out.println(ThreadUtil.getCurThreadName() + " end");
    }
}
