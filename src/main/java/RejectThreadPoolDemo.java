import java.util.concurrent.*;

public class RejectThreadPoolDemo {
    public static class MyTask implements Runnable{
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis()+ ":Thread ID:"+ Thread.currentThread().getId());
            try {
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        MyTask task = new MyTask();
        ExecutorService es = new ThreadPoolExecutor(5,5,0, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(10),
        Executors.defaultThreadFactory(),
                new RejectedExecutionHandler(){
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                System.out.println(runnable.toString()+"is discard");
            }
        });
        for (int i =0;i<Integer.MAX_VALUE;i++){
            es.submit(task);
            Thread.sleep(10);
        }
    }
}
