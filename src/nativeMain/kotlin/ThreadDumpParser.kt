import okio.FileSystem
import okio.Path

class ThreadDumpParser(private val path: Path) {
    private val text = FileSystem.SYSTEM.read(path) { readUtf8() }

    fun parse(): ThreadDump {
        val timestamp = text.lineSequence().firstOrNull { it.matches(TIMESTAMP_PATTERN) }
            ?: error("Invalid thread dump file: $path")
        return ThreadDump(
            path = path,
            timestamp = timestamp,
            threadInfos = parseThreadInfos().toList(),
        )
    }

    private fun parseThreadInfos(): Sequence<ThreadInfo> {
        return sequence {
            val context = ProcessingContext()
            text.lineSequence().forEach { line ->
                val matchResult = THREAD_NAME_PATTERN.find(line)
                if (matchResult == null) {
                    context.appendStacktrace(line)
                } else {
                    context.createThreadInfoOrNull()?.let { yield(it) }
                    context.nextThread(matchResult.groupValues[1])
                }
            }
            context.createThreadInfoOrNull()?.let { yield(it) }
        }
    }

    private data class ProcessingContext(
        private var threadName: String = "",
        private var stacktrace: StringBuilder = StringBuilder(),
    ) {
        fun createThreadInfoOrNull(): ThreadInfo? {
            if (threadName.isEmpty()) {
                return null
            }
            return ThreadInfo(ThreadName(threadName), Stacktrace(stacktrace.toString()))
        }

        fun nextThread(threadName: String) {
            this.threadName = threadName
            stacktrace = StringBuilder()
        }

        fun appendStacktrace(line: String) {
            stacktrace.appendLine(line)
        }
    }

    companion object {
        private val THREAD_NAME_PATTERN = """^"(.+)".*$""".toRegex()
        private val TIMESTAMP_PATTERN = """^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$""".toRegex()
    }
}

data class ThreadDump(
    val path: Path,
    val timestamp: String,
    val threadInfos: List<ThreadInfo>,
)

data class ThreadInfo(
    val threadName: ThreadName,
    val stacktrace: Stacktrace,
)

value class ThreadName(val value: String)

value class Stacktrace(val value: String)
