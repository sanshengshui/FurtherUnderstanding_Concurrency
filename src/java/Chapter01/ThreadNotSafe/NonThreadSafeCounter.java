package java.Chapter01.ThreadNotSafe;

/**
 * Created by jamesmsw on 17-1-15.
 */
public class NonThreadSafeCounter {
    private int counter=0;

    public void increment(){
        counter++;
    }

    public int get(){
        return counter;
    }
}
