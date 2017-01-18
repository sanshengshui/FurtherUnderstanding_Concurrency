package java.Guarded_Suspension;

import java.util.concurrent.Callable;

/**
 * Created by jamesmsw on 17-1-18.
 */
public abstract  class GuardedAction<V> implements Callable<V> {
    protected final Predicate guard;

    public GuardedAction(Predicate guard){
        this.guard=guard;
    }
}
