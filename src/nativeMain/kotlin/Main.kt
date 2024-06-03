import okio.Path.Companion.toPath

fun main(args: Array<String>) {
    val threadDumps = args.map { ThreadDumpParser(it.toPath()).parse() }
    println(ThreadDumpRenderer(threadDumps).render())
}
