package MultiThread.Runnable;

import MultiThread.Util.ThreadUtil;

public class CreateThreadByRunnable2 {
    public static final int MAX_TURN = 5;
    static int threadNo = 1;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = null;

        // 使用Runnable 的匿名函数创建和启动线程
        for (int i = 0; i < 2; i++) {
            thread = new Thread(new Runnable() { // 匿名实例
                @Override
                public void run() {
                    for (int j = 1; j < MAX_TURN; j++) {
                        System.out.println(ThreadUtil.getCurThreadName() + ", turn: " + j);
                    }
                    System.out.println(ThreadUtil.getCurThreadName() + " end");
                }
            }, "RunnableThread" + threadNo++);
            thread.start();
        }
        System.out.println(ThreadUtil.getCurThreadName() + " end");
    }
}
