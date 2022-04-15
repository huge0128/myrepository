package MultiThread.Runnable;

import MultiThread.Util.ThreadUtil;

public class CreateThreadByRunnable3 {
    public static final int MAX_TURN = 5;
    static int threadNo = 1;

    public static void main(String[] args) throws InterruptedException{
        Thread thread = null;
        // 使用Lambda 表达式创建和启动线程
        for (int i = 0; i < 2; i++) {
            thread = new Thread(() -> {
                for (int j = 1; j < MAX_TURN; j++) {
                    System.out.println(ThreadUtil.getCurThreadName() + ", turn: " + j);
                }
                System.out.println(ThreadUtil.getCurThreadName() + " end");
            }, "RunnableThread" + threadNo++);
            thread.start();
        }
        System.out.println(ThreadUtil.getCurThreadName() + " end");
    }
}
