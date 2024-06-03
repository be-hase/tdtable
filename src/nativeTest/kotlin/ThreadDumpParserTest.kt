import kotlin.test.Test

class ThreadDumpParserTest {
    @Test
    fun test() {
        val text = """
        856:
        2024-06-03 12:22:48
        Full thread dump OpenJDK 64-Bit Server VM (17.0.10+1-b1207.14 mixed mode):

        Threads class SMR info:
        _java_thread_list=0x000060000d026a40, length=103, elements={
        0x000000015d80b200, 0x000000015e02e000, 0x000000010186f200, 0x0000000101870400,
        0x0000000101870a00, 0x000000015c80c800, 0x000000015c80ce00, 0x000000015c80d400,
        0x0000000101871600, 0x0000000101860000, 0x000000013c814000, 0x000000015e030a00,
        0x000000015c85ee00, 0x000000015a843400, 0x000000013c87f400, 0x000000015c036a00,
        0x000000015e0ba400, 0x000000015b33e200, 0x000000015e0ca600, 0x000000013c8df000,
        0x000000015a96ea00, 0x000000013d292600, 0x000000015e0da400, 0x000000015c912200,
        0x000000015a978400, 0x000000015e0f3800, 0x000000015c285800, 0x000000015c1a6600,
        0x000000015e142a00, 0x0000000101abe000, 0x000000015c84d200, 0x000000013c8b9600,
        0x000000015e1ee200, 0x0000000101df7e00, 0x000000013cffee00, 0x000000013c97a600,
        0x000000015cbaec00, 0x000000013d038800, 0x000000015c6de200, 0x000000015b4bd400,
        0x000000015c281000, 0x000000013cae7000, 0x000000015cbb0800, 0x000000015d03ac00,
        0x000000015cbb0200, 0x000000013cac4600, 0x00000003be9b4000, 0x000000015d097200,
        0x00000003f309a600, 0x000000015b117800, 0x000000013caa4200, 0x000000015e11b200,
        0x000000015c8b0400, 0x00000003f3754c00, 0x000000015c2df800, 0x000000015c2e4c00,
        0x000000015adb7a00, 0x000000013cbe9600, 0x00000003bfca0a00, 0x000000044608f600,
        0x000000015aae7400, 0x00000003bf399e00, 0x000000015b198800, 0x0000000101a4c000,
        0x00000003ff291600, 0x0000000446082000, 0x000000013c87e400, 0x0000000446085000,
        0x000000044634f000, 0x000000015e58fc00, 0x000000015ce2d200, 0x00000003ed982800,
        0x0000000432a09400, 0x000000015d8dae00, 0x00000003ff0e7c00, 0x000000015d340000,
        0x0000000432afde00, 0x000000040b866200, 0x0000000432c31a00, 0x000000015a9f1200,
        0x000000015cd8d400, 0x000000015d0b1400, 0x0000000101fff400, 0x0000000403d9ea00,
        0x0000000454700a00, 0x00000004540faa00, 0x000000015b60b600, 0x0000000409be0800,
        0x0000000432d14800, 0x00000003d418b800, 0x00000003ba188000, 0x0000000432a23400,
        0x00000004329e7e00, 0x00000003bf575600, 0x000000015d7b9600, 0x0000000446579000,
        0x0000000101cfe000, 0x0000000454524800, 0x000000013c9ad800, 0x00000003ba1b0600,
        0x0000000432e59400, 0x00000003beeac800, 0x0000000454421a00
        }

        "main" #1 prio=5 os_prio=31 cpu=1228.24ms elapsed=8570.64s tid=0x000000015d80b200 nid=0xe03 waiting on condition  [0x000000016f4fe000]
           java.lang.Thread.State: TIMED_WAITING (parking)
                at jdk.internal.misc.Unsafe.park(java.base@17.0.10/Native Method)
                - parking to wait for  <0x00000006005048d0> (a kotlinx.coroutines.BlockingCoroutine)
                at java.util.concurrent.locks.LockSupport.parkNanos(java.base@17.0.10/LockSupport.java:252)
                at kotlinx.coroutines.BlockingCoroutine.joinBlocking(Builders.kt:88)
                at kotlinx.coroutines.BuildersKt__BuildersKt.runBlocking(Builders.kt:59)
                at kotlinx.coroutines.BuildersKt.runBlocking(Unknown Source)
                at kotlinx.coroutines.BuildersKt__BuildersKt.runBlockingdefault(Builders.kt:38)
                at kotlinx.coroutines.BuildersKt.runBlockingdefault(Unknown Source)
                at com.intellij.idea.Main.mainImpl(Main.kt:65)
                at com.intellij.idea.Main.mainImpldefault(Main.kt:51)
                at com.intellij.idea.Main.main(Main.kt:48)

        "Reference Handler" #2 daemon prio=10 os_prio=31 cpu=177.88ms elapsed=8570.59s tid=0x000000015e02e000 nid=0x4703 waiting on condition  [0x0000000288412000]
           java.lang.Thread.State: RUNNABLE
                at java.lang.ref.Reference.waitForReferencePendingList(java.base@17.0.10/Native Method)
                at java.lang.ref.Reference.processPendingReferences(java.base@17.0.10/Reference.java:253)
                at java.lang.ref.ReferenceReferenceHandler.run(java.base@17.0.10/Reference.java:215)

        "Finalizer" #3 daemon prio=8 os_prio=31 cpu=32.69ms elapsed=8570.59s tid=0x000000010186f200 nid=0x5003 in Object.wait()  [0x000000028861e000]
           java.lang.Thread.State: WAITING (on object monitor)
                at java.lang.Object.wait(java.base@17.0.10/Native Method)
                - waiting on <no object reference available>
                at java.lang.ref.ReferenceQueue.remove(java.base@17.0.10/ReferenceQueue.java:155)
                - locked <0x0000000600500378> (a java.lang.ref.ReferenceQueueLock)
                at java.lang.ref.ReferenceQueue.remove(java.base@17.0.10/ReferenceQueue.java:176)
                at java.lang.ref.FinalizerFinalizerThread.run(java.base@17.0.10/Finalizer.java:172)

        "server" - Thread t@94
           java.lang.Thread.State: WAITING
                at java.base@17.0.9/jdk.internal.misc.Unsafe.park(Native Method)
                - parking to wait for <556e4efb> (a java.util.concurrent.CountDownLatchSync)
                at java.base@17.0.9/java.util.concurrent.locks.LockSupport.park(LockSupport.java:211)
                at java.base@17.0.9/java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(AbstractQueuedSynchronizer.java:715)
                at java.base@17.0.9/java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1047)
                at java.base@17.0.9/java.util.concurrent.CountDownLatch.await(CountDownLatch.java:230)
                at app//reactor.core.publisher.BlockingSingleSubscriber.blockingGet(BlockingSingleSubscriber.java:90)
                at app//reactor.core.publisher.Mono.block(Mono.java:1728)
                at app//org.springframework.boot.web.embedded.netty.NettyWebServer${'$'}1.run(NettyWebServer.java:220)

           Locked ownable synchronizers:
                - None

        "DestroyJavaVM" - Thread t@95
           java.lang.Thread.State: RUNNABLE

           Locked ownable synchronizers:
                - None

        "Okio Watchdog" - Thread t@97
           java.lang.Thread.State: TIMED_WAITING
                at java.base@17.0.9/java.lang.Object.wait(Native Method)
                - waiting on <6280e5b1> (a java.lang.Class)
                at java.base@17.0.9/java.lang.Object.wait(Object.java:472)
                at app//io.pyroscope.okio.AsyncTimeoutCompanion.awaitTimeoutokio(AsyncTimeout.kt:318)
                at app//io.pyroscope.okio.AsyncTimeoutWatchdog.run(AsyncTimeout.kt:183)

           Locked ownable synchronizers:
                - None
        """.trimIndent()

//        val result = ThreadDumpParser("path", text).parse()
//        println(result.path)
//        println(result.timestamp)
//        result.stacktraces.forEach {
//            println(it.threadName)
//            println(it.stacktrace)
//        }
    }
}
