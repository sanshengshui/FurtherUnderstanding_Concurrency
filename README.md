![线程状态图](picture/线程图.png)

```java
public enum State{
    New,
    RUNNABLE,
    BLOCKED,
    WAITING,
    TIMED_WAITING,
    TERMINATED;
}
```