import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import okio.FileSystem
import okio.Path.Companion.toPath

class Tdtable : CliktCommand(
    printHelpOnEmptyArgs = true,
) {
    private val inputFiles: List<String> by argument(help = "input file. you can set multiple")
        .multiple(required = true)
    private val outputFile by option("-o", "--output-file", help = "output file")
        .required()

    override fun run() {
        val threadDumps = inputFiles.map { ThreadDumpParser(it.toPath()).parse() }
        val result = ThreadDumpRenderer(threadDumps).render()
        FileSystem.SYSTEM.write(outputFile.toPath()) { writeUtf8(result) }
    }
}

fun main(args: Array<String>) = Tdtable().main(args)
