import java.util.ArrayList;

public class ArrayListMultiThread {

    static ArrayList<Integer> a1 = new ArrayList<Integer>(10);

    public static class AddThread implements Runnable{
        public void run() {
            for (int i =0;i<1000000;i++){
                a1.add(i);
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread());
        Thread t2 = new Thread(new AddThread());
        t1.start();
        t2.start();
        t1.join();t2.join();
        System.out.println(a1.size());
    }
}
