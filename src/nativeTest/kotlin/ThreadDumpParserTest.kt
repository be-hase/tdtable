import assertk.all
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import okio.Path.Companion.toPath
import kotlin.test.Test

class ThreadDumpParserTest {
    @Test
    fun parse() {
        val result = ThreadDumpParser("src/nativeTest/resources/td.txt".toPath()).parse()
        println(result)

        assertThat(result.path.toString()).isEqualTo("src/nativeTest/resources/td.txt")
        assertThat(result.timestamp).isEqualTo("2024-06-04 02:09:09")

        val main = result.threadInfos.first { it.threadName.value == "main" }
        assertThat(main.stacktrace.value).all {
            contains(
                "\"main\" #1 [259] prio=6 os_prio=31 cpu=2746.35ms elapsed=268.10s tid=0x0000000153816c00 " +
                    "nid=259 runnable  [0x000000016b889000]",
            )
            contains("java.lang.Thread.State: RUNNABLE")
            contains("at org.eclipse.swt.internal.cocoa.OS.objc_msgSend_bool(Native Method)")
            contains("at org.eclipse.swt.widgets.Display.sleep(Display.java:5090)")
        }

        val worker1 = result.threadInfos.first { it.threadName.value == "Worker-1" }
        assertThat(worker1.stacktrace.value).all {
            contains(
                "\"Worker-1\" #59 [71939] prio=5 os_prio=31 cpu=1.21ms elapsed=266.12s " +
                    "tid=0x0000000126089200 nid=71939 in Object.wait()  [0x00000002b1a96000]",
            )
            contains("java.lang.Thread.State: TIMED_WAITING (on object monitor)")
            contains("at java.lang.Object.wait0(java.base@21.0.2/Native Method)")
            contains("- waiting on <no object reference available>")
        }
    }
}
