import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Long> {
    private static final int threshold = 10000;
    private long start;
    private long end;

    public CountTask(long start,long end){
        this.start = start;
        this.end = end;
    }

    @Override
    public  Long compute(){
        long sum = 0;
        boolean canCompute = (end-start)<threshold;
        if (canCompute){
            for (long i =start;i<end;i++){
                sum +=i;
            }
        }else {
            long step =(start+end)/100;
            ArrayList<CountTask> subTasks = new ArrayList<CountTask>();
            long pos = start;
            for (int i=0;i<100;i++){
                long lastOne = pos+step;
                if (lastOne>end){
                    lastOne=end;}
                CountTask subTask = new CountTask(pos,lastOne);
                pos+=step+1;
                subTasks.add(subTask);
                subTask.fork();
            }
            for (CountTask t:subTasks){
                sum += t.join();
            }
        }
        return sum;
    }
    public static  void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(0,200000);
        ForkJoinTask<Long> result = forkJoinPool.submit(task);
        long res = result.get();
        System.out.println("sum="+res);
    }
}
