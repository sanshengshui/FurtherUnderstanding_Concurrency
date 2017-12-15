import java.util.concurrent.*;

public class MyThreadFactory {
    public static void main(String[] args)throws InterruptedException{
        RejectThreadPoolDemo.MyTask task =new RejectThreadPoolDemo.MyTask();
        ExecutorService es = new ThreadPoolExecutor(5, 5, 0, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable runnable) {
                        Thread t = new Thread(runnable);
                        t.setDaemon(true);
                        System.out.println("create "+ t);
                        return t;
                    }
                });
        for (int i =0;i<5;i++){
            es.submit(task);
        }
        Thread.sleep(2000);
    }
}
