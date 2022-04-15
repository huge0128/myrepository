package MultiThread.ThreadClass;

public class EmptyThreadDemo {
    public static void main(String[] args) throws InterruptedException{
        Thread thread = new Thread();
        System.out.println("ThreadName:" + thread.getName());
        System.out.println("ThreadId:" + thread.getId());
        System.out.println("ThreadPriority: " + thread.getPriority());
        thread.start();
    }
}
