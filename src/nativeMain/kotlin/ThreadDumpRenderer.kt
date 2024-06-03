import kotlinx.html.body
import kotlinx.html.br
import kotlinx.html.code
import kotlinx.html.html
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.pre
import kotlinx.html.stream.appendHTML
import kotlinx.html.table
import kotlinx.html.td
import kotlinx.html.th
import kotlinx.html.tr

class ThreadDumpRenderer(private val threadDumps: List<ThreadDump>) {

    private fun data(): Map<ThreadDumpIdentifier, Map<ThreadName, List<Stacktrace>>> {
        return threadDumps.associateBy(
            { threadDump -> ThreadDumpIdentifier(threadDump.path.toString(), threadDump.timestamp) },
            { threadDump ->
                threadDump.threadInfos.groupBy(
                    { it.threadName },
                    { it.stacktrace },
                )
            },
        )
    }

    private fun columns(): List<ThreadDumpIdentifier> {
        return threadDumps
            .map { ThreadDumpIdentifier(it.path.toString(), it.timestamp) }
            .sortedBy { it.timestamp }
    }

    private fun rows(): List<ThreadName> {
        return threadDumps
            .flatMap { threadDump -> threadDump.threadInfos.map { it.threadName } }
            .distinct()
            .sortedBy { it.value }
    }

    fun render(): String {
        val data = data()
        return StringBuilder().appendHTML().html {
            body {
                meta {
                    link {
                        href = "https://cdn.jsdelivr.net/npm/water.css@2/out/water.css"
                        rel = "stylesheet"
                    }
                }
                table {
                    tr {
                        th { +"\\" }
                        columns().forEach { column ->
                            th {
                                +column.timestamp
                                br { }
                                +column.path
                            }
                        }
                    }
                    rows().forEach { row ->
                        tr {
                            th { +row.value }
                            columns().forEach { column ->
                                val stacktraces = data[column]?.get(row) ?: emptyList()
                                td {
                                    stacktraces.forEach { stacktrace ->
                                        pre {
                                            code {
                                                +stacktrace.value
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.toString()
    }

    private data class ThreadDumpIdentifier(
        val path: String,
        val timestamp: String,
    )
}
