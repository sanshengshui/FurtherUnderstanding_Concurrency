package java.Chapter01.ThreadSafe;

/**
 * Created by jamesmsw on 17-1-15.
 */
public class ThreadSafeCounter {
    private int counter=0;

    public void increment(){
        synchronized (this){
            counter++;
        }
    }

    public int get(){
        synchronized (this){
            return counter;
        }
    }
}
