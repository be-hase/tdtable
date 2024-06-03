import kotlinx.html.InputType
import kotlinx.html.ScriptType
import kotlinx.html.body
import kotlinx.html.br
import kotlinx.html.classes
import kotlinx.html.code
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.pre
import kotlinx.html.script
import kotlinx.html.span
import kotlinx.html.stream.appendHTML
import kotlinx.html.style
import kotlinx.html.table
import kotlinx.html.tbody
import kotlinx.html.td
import kotlinx.html.th
import kotlinx.html.thead
import kotlinx.html.title
import kotlinx.html.tr
import kotlinx.html.unsafe

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
            head {
                title = "Thread Dump Table Viewer"
                meta {
                    link {
                        href = "https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
                        rel = "stylesheet"
                        integrity = "sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuz" +
                            "x5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
                    }
                }
                style { unsafe { +STYLE } }
            }
            body {
                table {
                    thead {
                        tr {
                            th {
                                id = "thread-name-filter"
                                span {
                                    input {
                                        type = InputType.text
                                        placeholder = "Filter by thread name"
                                    }
                                }
                            }
                            columns().forEach { column ->
                                th {
                                    span {
                                        +column.timestamp
                                        br { }
                                        +column.path
                                    }
                                }
                            }
                        }
                    }
                    tbody {
                        rows().forEach { row ->
                            tr {
                                th {
                                    classes = setOf("thread-name")
                                    span {
                                        +row.value
                                    }
                                }
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
                script(type = ScriptType.textJavaScript) {
                    unsafe { +JAVA_SCRIPT }
                }
            }
        }.toString()
    }

    private data class ThreadDumpIdentifier(
        val path: String,
        val timestamp: String,
    )

    companion object {
        // language=css
        private val STYLE = """
        body {
            background-color: rgb(18, 23, 29);
        }
        table {
            color: #dbdbdb;
        }
        th {
            background-color: rgb(2, 5, 11);
            font-size: 12px;
            padding: 10px;
            text-align: left;
            vertical-align: top;
            word-break: break-all;
        }
        td {
            padding: 10px;
            vertical-align: top;
        }
        thead th span {
            display: block; width: 620px;
        }
        pre {
            background-color: rgb(30, 33, 39);
            padding: 10px;
            border-radius: 5px;
            width: 600px;
            overflow-x: scroll;
            font-size: 12px;
        }
        .hidden {
            display: none;
        }
        #thread-name-filter span, .thread-name span{
            display: block;
            width: 250px;
        }
        #thread-name-filter input {
            padding: 5px;
            width: 100%;
        }
        """.trimIndent()

        // language=javascript
        private val JAVA_SCRIPT = """
        const rows = document.querySelectorAll(".thread-name");

        document.getElementById("thread-name-filter").addEventListener("keyup", (e) => {
            const filterValue = e.target.value.toLowerCase();
            rows.forEach((row) => {
                const threadName = row.textContent.toLowerCase();
                if (threadName.indexOf(filterValue) > -1) {
                    row.parentElement.classList.remove("hidden");
                } else {
                    row.parentElement.classList.add("hidden");
                }
            });
        });
        """.trimIndent()
    }
}
