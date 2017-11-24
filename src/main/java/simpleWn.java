/**
 * 等待(wait)和通知(notify)
 */
public class simpleWn {
    public static void main(String[] args){
        Thread t1 = new T1();
        Thread t2 = new T2();
        t1.start();
        t2.start();
    }
    final static Object object = new Object();
    private static class T1 extends Thread{
        @Override
        public void run() {
            synchronized (object){
                System.out.println(System.currentTimeMillis()+":T1 start!");
                try {
                    System.out.println(System.currentTimeMillis()+":T1 wait for object");
                    object.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis()+":T1 end!");
            }
        }
    }

    private static class T2 extends Thread{
        @Override
        public void run() {
            synchronized (object){
                System.out.println(System.currentTimeMillis()+":T2 start! notify one thread");
                object.notify();
                System.out.println(System.currentTimeMillis()+":T2 end!");
                try {
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                }
            }
        }
    }

}
