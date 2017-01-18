package java.Guarded_Suspension;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by jamesmsw on 17-1-18.
 */
public class ConditionVarBlocker implements Blocker {

    private final Lock lock;
    private final Condition condition;

    public ConditionVarBlocker(Lock lock){
        this.lock=lock;
        this.condition=lock.newCondition();
    }


    @Override
    public <V> V callWithGuard(GuardedAction<V> guardedAction) throws Exception {
       lock.lockInterruptibly();
        V result;
        try {
            final Predicate guard=guardedAction.guard;
            while (!guard.evaluate()){
                condition.await();
            }
            result=guardedAction.call();
            return  result;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void signalAfter(Callable<Boolean> stateOperation) throws Exception {
        lock.lockInterruptibly();
        try{
            if (stateOperation.call()){
                condition.signal();
            }
        }finally {
            lock.unlock();
        }

    }

    @Override
    public void signal() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            condition.signal();
        }finally {
            lock.unlock();
        }

    }

    @Override
    public void broadcastAfter(Callable<Boolean> stateOperation) throws Exception {
        lock.lockInterruptibly();
        try {
            if (stateOperation.call()){
                condition.signalAll();
            }
        }finally {
            lock.unlock();
        }

    }
}
