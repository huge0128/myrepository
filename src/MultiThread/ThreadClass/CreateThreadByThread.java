package MultiThread.ThreadClass;

public class CreateThreadByThread {
    public static final int MAX_TURN = 5;
    public static String getCurThreadName() {
        return Thread.currentThread().getName();
    }

    // index
    static int threadNo = 1;

    static class DemoThread extends Thread {
        public DemoThread() {
            super("DemoThread-" + threadNo++);
        }

        public void run() {
            for (int i = 1; i  < MAX_TURN; i++) {
                System.out.println(getName() + ", turn:" + i);
            }
            System.out.println(getName() + " end");
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Thread thread = null;
        //
        for (int i = 0; i < 2; i++) {
            thread = new DemoThread();
            thread.start();
        }

        System.out.println(getCurThreadName() + "end");
    }



}
