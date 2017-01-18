package java.Guarded_Suspension;

import java.util.concurrent.Callable;

/**
 * Created by jamesmsw on 17-1-18.
 */
public interface Blocker {
    /*
    在保护条件成立时执行目标动作;否则阻塞当前线程，直到保护条件成立。
    ＠Param guardedAction　带保护条件的目标动作
     */
    <V> V callWithGuard(GuardedAction<V> guardedAction) throws Exception;

    /*
    执行stateOperation所指定的操作后,决定是否唤醒本Blocker
    所暂挂的所有线程中的一个线程。

    @param stateOperation
    更改状态的操作,其call方法的返回值为true时,该方法才会唤醒被暂挂的线程
     */

    void signalAfter(Callable<Boolean> stateOperation) throws Exception;

    void signal() throws InterruptedException;

    void broadcastAfter(Callable<Boolean> stateOperation) throws Exception;
}
