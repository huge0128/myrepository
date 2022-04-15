package MultiThread.Runnable;

import MultiThread.Util.ThreadUtil;

public class CreateThreadByRunnable {
    public static final int MAX_TURN = 5;
    static int threadNo = 1;

    static class RunTarget implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i < MAX_TURN; i++) {
                System.out.println(ThreadUtil.getCurThreadName() + ", turn: " + i);
            }
            System.out.println(ThreadUtil.getCurThreadName() + "end");
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Thread thread = null;
        for (int i = 0; i < 2; i++) {
            Runnable target = new RunTarget();
            thread = new Thread(target, "RunnableThread" + threadNo++);
            /**
             * 三种构造器
             * public Thread(Runnable target)
             * public Thread(Runnable target, String name)
             * public Thread(ThreadGroup group, Runnable target)
             * */
            thread.start();
        }
    }

}
