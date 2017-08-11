# 参考书籍  　　
- Java多线程编程实战指南(设计模式篇)  　　
- Java Concurrency in Practice
- Java 7 Concurrency Cookbook  

# Java工具
- jps

```
描述
    jps位于jdk的bin目录下，其作用是显示当前系统的java进程情况，及其id号。 jps相当于Solaris进程工具ps。不象”pgrep java”
或”ps -ef grep java”，jps并不使用应用程序名来查找JVM实例。因此，它查找所有的Java应用程序，包括即使没有使用java执行体的
那种（例如，定制的启动 器）。另外，jps仅查找当前用户的Java进程，而不是当前系统中的所有进程。

位置

    我们知道，很多Java命令都在jdk的JAVA_HOME/bin/目录下面，jps也不例外，他就在bin目录下，所以，他是java自带的一个命令。

功能

    jps(Java Virtual Machine Process Status Tool)是JDK 1.5提供的一个显示当前所有java进程pid的命令，简单实用，非常适合在
linux/unix平台上简单察看当前java进程的一些简单情况。
 原理

    jdk中的jps命令可以显示当前运行的java进程以及相关参数，它的实现机制如下：
    java程序在启动以后，会在java.io.tmpdir指定的目录下，就是临时文件夹里，生成一个类似于hsperfdata_User的文件夹，这个文件
夹里（在Linux中为/tmp/hsperfdata_{userName}/），有几个文件，名字就是java进程的pid，因此列出当前运行的java进程，只是把这个
目录里的文件名列一下而已。 至于系统的参数什么，就可以解析这几个文件获得。

 使用
    -q 只显示pid，不显示class名称,jar文件名和传递给main 方法的参数
    -m 输出传递给main 方法的参数，在嵌入式jvm上可能是null， 在这里，在启动main方法的时候，我给String[] args传递两个参数。
hollis,chuang,执行jsp -m:
    -l 输出应用程序main class的完整package名 或者 应用程序的jar文件完整路径名
    -v 输出传递给JVM的参数 在这里，在启动main方法的时候，我给jvm传递一个参数：-Dfile.encoding=UTF-8,执行jps -v：
    PS:jps命令有个地方很不好，似乎只能显示当前用户的java进程，要显示其他用户的还是只能用unix/linux的ps命令。
    jps是我最常用的java命令。使用jps可以查看当前有哪些Java进程处于运行状态。如果我运行了一个web应用（使用tomcat、jboss、
jetty等启动）的时候，我就可以使用jps查看启动情况。有的时候我想知道这个应用的日志会输出到哪里，或者启动的时候使用了哪些
javaagent，那么我可以使用jps -v 查看进程的jvm参数情况。

 JPS失效处理
    现象： 用ps -ef|grep java能看到启动的java进程，但是用jps查看却不存在该进程的id。待会儿解释过之后就能知道在该情况下，
jconsole、jvisualvm可能无法监控该进程，其他java自带工具也可能无法使用分析： jps、jconsole、jvisualvm等工具的数据来源就是
这个文件（/tmp/hsperfdata_userName/pid)。所以当该文件不存在或是无法读取时就会出现jps无法查看该进程号，jconsole无法监控等问题
    原因：
    （1）、磁盘读写、目录权限问题 若该用户没有权限写/tmp目录或是磁盘已满，则无法创建/tmp/hsperfdata_userName/pid文件。
或该文件已经生成，但用户没有读权限
    （2）、临时文件丢失，被删除或是定期清理 对于linux机器，一般都会存在定时任务对临时文件夹进行清理，导致/tmp目录被清空。
这也是我第一次碰到该现象的原因。常用的可能定时删除临时目录的工具为crontab、redhat的tmpwatch、ubuntu的tmpreaper等等这个导
致的现象可能会是这样，用jconsole监控进程，发现在某一时段后进程仍然存在，但是却没有监控信息了。
    （3）、java进程信息文件存储地址被设置，不在/tmp目录下 上面我们在介绍时说默认会在/tmp/hsperfdata_userName目录保存进程
信息，但由于以上1、2所述原因，可能导致该文件无法生成或是丢失，所以java启动时提供了参数(-Djava.io.tmpdir)，可以对这个文件
的位置进行设置，而jps、jconsole都只会从/tmp目录读取，而无法从设置后的目录读物信息，这是我第二次碰到该现象的原因
    附：
    1.如何给main传递参数 在eclipse中，鼠标右键->Run As->Run COnfiguations->Arguments->在Program arguments中写下要传的参数值
    2.如何给JVM传递参数 在eclipse中，鼠标右键->Run As->Run COnfiguations->Arguments->在VM arguments中写下要传的参数值
（一般以-D开头）
```
<p align="center"><img src ="picture/jps.PNG" alt="JPS logo" /></p>

- Jstack
```
描述
    jstack是java虚拟机自带的一种堆栈跟踪工具。

 功能
    jstack用于生成java虚拟机当前时刻的线程快照。线程快照是当前java虚拟机内每一条线程正在执行的方法堆栈的集合，生成线程快照
的主要目的是定位线程出现长时间停顿的原因，如线程间死锁、死循环、请求外部资源导致的长时间等待等。 线程出现停顿的时候通过
jstack来查看各个线程的调用堆栈，就可以知道没有响应的线程到底在后台做什么事情，或者等待什么资源。 如果java程序崩溃生成
core文件，jstack工具可以用来获得core文件的java stack和native stack的信息，从而可以轻松地知道java程序是如何崩溃和在程序何处
发生问题。另外，jstack工具还可以附属到正在运行的java程序中，看到当时运行的java程序的java stack和native stack的信息,
如果现在运行的java程序呈现hung的状态，jstack是非常有用的。
    So,jstack命令主要用来查看Java线程的调用堆栈的，可以用来分析线程问题（如死锁）。
线程状态
    想要通过jstack命令来分析线程的情况的话，首先要知道线程都有哪些状态，下面这些状态是我们使用jstack命令查看线程堆栈信息
时可能会看到的线程的几种状态：
    NEW,未启动的。不会出现在Dump中。
    RUNNABLE,在虚拟机内执行的。
    BLOCKED,受阻塞并等待监视器锁。
    WATING,无限期等待另一个线程执行特定操作。
   TIMED_WATING,有时限的等待另一个线程的特定操作。
   TERMINATED,已退出的。
Monitor
    在多线程的 JAVA程序中，实现线程之间的同步，就要说说 Monitor。 Monitor是 Java中用以实现线程之间的互斥与协作的主要手段，
它可以看成是对象或者 Class的锁。每一个对象都有，也仅有一个 monitor。图1，描述了线程和 Monitor之间关系，以 及线程
的状态转换图：
    进入区(Entrt Set):
    表示线程通过synchronized要求获取对象的锁。如果对象未被锁住,则迚入拥有者;否则则在进入区等待。一旦对象锁被其他线程释放,
立即参与竞争。
    拥有者(The Owner):
    表示某一线程成功竞争到对象锁。
    等待区(Wait Set):
    表示线程通过对象的wait方法,释放对象的锁,并在等待区等待被唤醒。
    从下图可以看出，一个 Monitor在某个时刻，只能被一个线程拥有，该线程就是 “Active Thread”，而其它线程都是 
“Waiting Thread”，分别在两个队列 “ Entry Set”和 “Wait Set”里面等候。在 “Entry Set”中等待的线程状态是 “Waiting 
for monitor entry”，而在 “Wait Set”中等待的线程状态是 “in Object.wait()”。 先看 “Entry Set”里面的线程。我们称被 
synchronized保护起来的代码段为临界区。当一个线程申请进入临界区时，它就进入了 “Entry Set”队列。
调用修饰
    表示线程在方法调用时,额外的重要的操作。线程Dump分析的重要信息。修饰上方的方法调用。
    locked <地址> 目标：使用synchronized申请对象锁成功,监视器的拥有者。
    waiting to lock <地址> 目标：使用synchronized申请对象锁未成功,在迚入区等待。
    waiting on <地址> 目标：使用synchronized申请对象锁成功后,释放锁幵在等待区等待。
    parking to wait for <地址> 目标
线程Dump的分析
    原则
    结合代码阅读的推理。需要线程Dump和源码的相互推导和印证。
    造成Bug的根源往往丌会在调用栈上直接体现,一定格外注意线程当前调用之前的所有调用。
入手点
    进入区等待
    线程状态BLOCKED,线程动作wait on monitor entry,调用修饰waiting to lock总是一起出现。表示在代码级别已经存在冲突的调用。
必然有问题的代码,需要尽可能减少其发生。
    同步块阻塞
    一个线程锁住某对象,大量其他线程在该对象上等待。
    持续运行的IO IO操作是可以以RUNNABLE状态达成阻塞。例如:数据库死锁、网络读写。 格外注意对IO线程的真实状态的分析。
一般来说,被捕捉到RUNNABLE的IO调用,都是有问题的。
    以下堆栈显示：
     线程状态为RUNNABLE。 调用栈在SocketInputStream或SocketImpl上,socketRead0等方法。 调用栈包含了jdbc相关的包。很可能
发生了数据库死锁
    分线程调度的休眠
正常的线程池等待
可疑的线程等待

------入手点总结------
Wait on monitor entry : 被组塞的，肯定有问题
runnable              :注意IO线程
in Object.wait()      ：注意非线程池等待
   
使用
    -F 当’jstack [-l] pid’没有相应的时候强制打印栈信息
    -l 长列表. 打印关于锁的附加信息,例如属于java.util.concurrent的ownable synchronizers列表. 
    -m 打印java和native c/c++框架的所有栈信息. 
    -h | -help打印帮助信息 
    pid 需要被打印配置信息的java进程id,可以用jps查询.    
---step1---
先是有jps查看进程号：
hollis@hos:~$ jps
29788 JStackDemo1
29834 Jps
22385 org.eclipse.equinox.launcher_1.3.0.v20130327-1440.jar
---step2---  
然后使用jstack 查看堆栈信息：  
hollis@hos:~$ jstack 29788
2015-04-17 23:47:31
...此处省略若干内容...
"main" prio=10 tid=0x00007f197800a000 nid=0x7462 runnable [0x00007f197f7e1000]
   java.lang.Thread.State: RUNNABLE
    at javaCommand.JStackDemo1.main(JStackDemo1.java:7)
其他
虚拟机执行Full GC时,会阻塞所有的用户线程。因此,即时获取到同步锁的线程也有可能被阻塞。 在查看线程Dump时,首先查看内存使用情况。    
```
<p align="center"><img src ="picture/Monitor.PNG" alt="horizon" /></p>

- Jmap
```
描述
    jmap是JDK自带的工具软件，主要用于打印指定Java进程(或核心文件、远程调试服务器)的共享对象内存映射或堆内存细节。可以使用jmap生成Heap
Dump。
什么是堆Dump
    堆Dump是反应Java堆使用情况的内存镜像，其中主要包括系统信息、虚拟机属性、完整的线程Dump、所有类和对象的状态等。 一般，在内存不足、
GC异常等情况下，我们就会怀疑有内存泄露。这个时候我们就可以制作堆Dump来查看具体情况。分析原因。
基础知识
    outOfMemoryError 年老代内存不足。
    outOfMemoryError:PermGen Space 永久代内存不足。
    outOfMemoryError:GC overhead limit exceed 垃圾回收时间占用系统运行时间的98%或以上。
```




