import ThreadDumpRenderer.ThreadDumpIdentifier
import assertk.assertThat
import assertk.assertions.isEqualTo
import okio.Path.Companion.toPath
import kotlin.test.Test

class ThreadDumpRendererTest {
    private val threadDumps = listOf(
        ThreadDump(
            path = "path0".toPath(),
            timestamp = "timestamp0",
            threadInfos = listOf(
                ThreadInfo(threadName = ThreadName("thread0"), stacktrace = Stacktrace("stacktrace0")),
                ThreadInfo(threadName = ThreadName("thread1"), stacktrace = Stacktrace("stacktrace1")),
                ThreadInfo(threadName = ThreadName("thread2"), stacktrace = Stacktrace("stacktrace2")),
                ThreadInfo(threadName = ThreadName("thread2"), stacktrace = Stacktrace("stacktrace2-other")),
            ),
        ),
        ThreadDump(
            path = "path1".toPath(),
            timestamp = "timestamp1",
            threadInfos = listOf(
                ThreadInfo(threadName = ThreadName("thread1"), stacktrace = Stacktrace("stacktrace1")),
                ThreadInfo(threadName = ThreadName("thread2"), stacktrace = Stacktrace("stacktrace2")),
                ThreadInfo(threadName = ThreadName("thread3"), stacktrace = Stacktrace("stacktrace3")),
            ),
        ),
    )

    private val target = ThreadDumpRenderer(threadDumps)

    @Test
    fun data() {
        val result = target.data()
        assertThat(result).isEqualTo(
            mapOf(
                ThreadDumpIdentifier("path0", "timestamp0") to mapOf(
                    ThreadName("thread0") to listOf("stacktrace0").map { Stacktrace(it) },
                    ThreadName("thread1") to listOf("stacktrace1").map { Stacktrace(it) },
                    ThreadName("thread2") to listOf("stacktrace2", "stacktrace2-other").map { Stacktrace(it) },
                ),
                ThreadDumpIdentifier("path1", "timestamp1") to mapOf(
                    ThreadName("thread1") to listOf("stacktrace1").map { Stacktrace(it) },
                    ThreadName("thread2") to listOf("stacktrace2").map { Stacktrace(it) },
                    ThreadName("thread3") to listOf("stacktrace3").map { Stacktrace(it) },
                ),
            ),
        )
    }

    @Test
    fun columns() {
        val result = target.columns()
        assertThat(result).isEqualTo(
            listOf(
                ThreadDumpIdentifier("path0", "timestamp0"),
                ThreadDumpIdentifier("path1", "timestamp1"),
            ),
        )
    }

    @Test
    fun rows() {
        val result = target.rows()
        assertThat(result).isEqualTo(
            listOf("thread0", "thread1", "thread2", "thread3").map { ThreadName(it) },
        )
    }
}
