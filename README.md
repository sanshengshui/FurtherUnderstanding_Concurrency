# 参考书籍  　　
- Java多线程编程实战指南(设计模式篇)  　　
- Java Concurrency in Practice
- Java 7 Concurrency Cookbook  

# Java工具
- jps
## 描述
```text
    jps位于jdk的bin目录下，其作用是显示当前系统的java进程情况，及其id号。 jps相当于Solaris进程工具ps。不象”pgrep java”
或”ps -ef grep java”，jps并不使用应用程序名来查找JVM实例。因此，它查找所有的Java应用程序，包括即使没有使用java执行体的
那种（例如，定制的启动 器）。另外，jps仅查找当前用户的Java进程，而不是当前系统中的所有进程。
```
## 位置
```text
    我们知道，很多Java命令都在jdk的JAVA_HOME/bin/目录下面，jps也不例外，他就在bin目录下，所以，他是java自带的一个命令。
```
## 功能
```text
    jps(Java Virtual Machine Process Status Tool)是JDK 1.5提供的一个显示当前所有java进程pid的命令，简单实用，非常适合在
linux/unix平台上简单察看当前java进程的一些简单情况。
```
## 原理
```text
    jdk中的jps命令可以显示当前运行的java进程以及相关参数，它的实现机制如下：
    java程序在启动以后，会在java.io.tmpdir指定的目录下，就是临时文件夹里，生成一个类似于hsperfdata_User的文件夹，这个文件
夹里（在Linux中为/tmp/hsperfdata_{userName}/），有几个文件，名字就是java进程的pid，因此列出当前运行的java进程，只是把这个
目录里的文件名列一下而已。 至于系统的参数什么，就可以解析这几个文件获得。
```
## 使用
```text
    -q 只显示pid，不显示class名称,jar文件名和传递给main 方法的参数
    -m 输出传递给main 方法的参数，在嵌入式jvm上可能是null， 在这里，在启动main方法的时候，我给String[] args传递两个参数。hollis,chuang,执行jsp -m:
    -l 输出应用程序main class的完整package名 或者 应用程序的jar文件完整路径名
    -v 输出传递给JVM的参数 在这里，在启动main方法的时候，我给jvm传递一个参数：-Dfile.encoding=UTF-8,执行jps -v：
    PS:jps命令有个地方很不好，似乎只能显示当前用户的java进程，要显示其他用户的还是只能用unix/linux的ps命令。
    jps是我最常用的java命令。使用jps可以查看当前有哪些Java进程处于运行状态。如果我运行了一个web应用（使用tomcat、jboss、
jetty等启动）的时候，我就可以使用jps查看启动情况。有的时候我想知道这个应用的日志会输出到哪里，或者启动的时候使用了哪些
javaagent，那么我可以使用jps -v 查看进程的jvm参数情况。
```
<p align="center"><img src ="picture/jps.PNG" alt="JPS logo" /></p>

## JPS失效处理
```text
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
```
多线程共享变量的情况下，为了保证数据的一致性，往往需要对这些变量的访问进行加锁，
而锁的本身又会带来一些问题和开销。  

Immutable Object模式使得我们可以在不使用锁的情况下，既保证共享变量访问的线程安全
，又能避免引入锁可能带来的问题和开销。    

多线程环境下，一个对象常常会被多个线程共享。这种情况下，如果存在多个线程并发地
修改该对象的状态或者一个线程访问该对象的状态而另外一个线程试图修改该对象的状态，

我们不得不做一些同步访问控制以保证数据一致性。而这些同步访问控制，
如显式锁和CAS操作，会带来额外的开销和问题，如上下文切换，等待时间和ABA问题等。

Immutable Object模式的意图是通过使用对外可见的状态不可变的对象，
使得被共享对象"天生"具有线程安全性，而无须额外地同步访问控制。

从而既保证了数据一致性，又避免了同步访问控制所产生的额外开销和问题，也简化了编程。
所谓状态不可变的对象，即对象一经创建，其对外可见的状态就保持不变，

例如Java中的String和Integer。这点固然容易理解，
但还不足以知道我们在实际工作中运用Immutable Object模式。



Client           ImmutableObject       Manipulator

ImmutableObject :负责存储一组不可变状态。
该参与者不对外暴露任何可以修改其状态的方法，其主要方法及职责如下。
一个严格意义上不可变对象要满足以下所有条件。

１.类本身使用final修饰:防止其子类改变其定义的行为。

2. 所有字段都是用final修饰的:使用final修饰不仅仅是从语义上说明所修饰字段的引用
不可改变。更重要的是这个语义在多线程环境下由JMM保证了被修饰字段所引用对象的初始化
安全，即final修饰的字段在其他线程可见时,它必定是初始化完成的。
相反,非final修饰的字段由于缺少这种保证,可能导致一个线程"看到"一个字段的时候，
它还没有被初始化完成，从而可能导致一些不可预料的结果。

3.在对象的创建过程中,this关键字没有泄露给其他类:防止其他类在对象创建过程中
修改其状态。

４．任何字段,若其引用了其他状态可变的对象(如集合,数组等),则这些字段必须是
private修饰的，并且这些字段值不能对外暴露。
若其相关方法要返回这些字段值，应该进行防御性复制

Immutable Object模式特别适用于以下场景。

(1)被建模对象的状态变化不频繁
正如本章案例所展示的,这种场景下可以设置一个专门的线程(Manipulator参与者所在的
线程)用于在被建模对象状态变化时创建新的不可变对象。而其他线程则只是读取不可变对象
的状态。此场景下的一个小技巧是Manipulator对不可变对象的引用采用volatile关键字修饰，
既可以避免使用显示锁(synchronized),又可以保证多线程间的内存可见性。

(2)同时对一组相关的数据进行写操作，因此需要保证原子性：
此场景为了保证操作的原子性，通常的做法是使用显示锁。
但若采用Immutable Object模式，将这一组相关的数据"组合"成一个不可变对象，
则对这一组数据的操作就可以无须加显示锁也能保证原子性，这及简化了编程，
有提高了代码运行效率。本章开头所举的车辆位置跟踪的例子正是这种场景。

(3)使用某个对象作为安全的HashMap的Key:我们知道，
一个对象作为HashMap的key被"放入"HashMap之后，如该对象状态变化导致了其Hash 
Code的变化，则会导致后面在用同样的对象作为key去get的时候无法获取关联的值,
尽管该HashMap中的确存在以该对象为key的条目，相反，
由于不可变对象的状态不变，因此其Hash Code也不变。
这使得不可变对象非常适于用作HashMap的Key.


Guarded Suspension模式的核心是一个受保护方法。
该方法执行其所要真正执行的操作时需要满足特定的条件(Predicate,以下称之为保护条件)
。当该条件不满足时,执行受保护方法的线程会被挂起进入等待状态，
直达该条件满足时该线程才会继续运行。此时，受保护方法才会真正执行其所要执行的操作。
为方便起见，以下称受保护方法所要真正执行的操作为目标动作。


告警功能模块。

该模块的主要功能是将其接受到的告警信息发送给告警服务器。

该模块中的类AlarmAgent负责与告警服务器进行对接。

AlarmAgent的sendAlarm方法负责通过网络连接(Socket连接)将告警信息发送到告警服务器。

AlarmAgent创建了一个专门的线程用于其与告警服务器建立网路连接。
因此，sendAlarm方法被调用的时候,
连接线程可能还没有完成网络连接的建立。
此时,sendAlarm方法应该等待连接线程建立好网络连接。


另外，即便连接线程建立好了网络连接，中途也可能由于某些原因出现
与告警服务器断连的情况。此时,sendAlarm方法需要等待心跳(Heartbeat)任务重新建立好
连接才能上报告警信息。也就是说,sendAlarm方法必须在AlarmAgent与告警服务器
的网络连接建立成功的情况下才能执行其所要执行的操作。

若AlarmAgent与告警服务器的连接未建立(或者连接中断)，
sendAlarm方法的执行线程应该暂挂直到连接建立完毕(或者恢复)
```

