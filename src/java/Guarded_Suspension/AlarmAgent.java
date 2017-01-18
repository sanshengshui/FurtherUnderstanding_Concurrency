package java.Guarded_Suspension;

import jdk.nashorn.internal.runtime.Debug;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;

/**
 * Created by jamesmsw on 17-1-18.
 */
public class AlarmAgent {
    private volatile boolean connectedToServer=false;

    private final  Predicate agentConnected=new Predicate() {
        @Override
        public boolean evaluate() {
            return false;
        }
    };


    private final Blocker blocker=new ConditionVarBlocker();

    private final Timer heartbeatTimer=new Timer(true);

    public void init(){
        Thread connectingThread =new Thread(new ConnectingTask());
        connectingThread.start();
        heartbeatTimer.schedule(new HeartbeatTask(),60000,2000);
    }
    public void disconnect(){
        //省略其他代码
        //Debug.info("disconnected from alarm server");
        connectedToServer=false;
    }


    private class ConnectingTask implements Runnable{
        @Override
        public void run() {
            //省略其他代码
            //模拟连接操作耗时
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    protected  void onDisconnected(){
        connectedToServer=false;
    }
    protected  void onConnected(){
        try {
            blocker.signalAfter(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    connectedToServer=true;
                    return  Boolean.TRUE;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private class HeartbeatTask extends TimerTask{
        @Override
        public void run() {
            if (!testConnection()) {
                onDisconnected();
                reconnect();
            }
        }
        private boolean testConnection(){
            return true;
        }

        private void reconnect(){
            ConnectingTask connectingThread=new ConnectingTask();
            //直接在心跳定时器线程中执行
            connectingThread.run();
        }

    }






}
