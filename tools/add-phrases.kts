/**
 * This script allows to add new phrases to the lists
 * 
 * In order to run it, [cd] to the root directory of the project and then run:
 * kotlinc -script tools/add-phrases.kts
 **/

import java.io.*
import kotlin.io.*
import kotlin.math.*

val SPLITTER = "->"
val LOCATION = File("app/src/main/resources/")
val INPUTS = LOCATION.resolve("inputs.txt")
val LABELS = LOCATION.resolve("labels.txt")

val inputs = ArrayList<Pair<String, Float>>(500).apply {
	INPUTS.readText().split('\n').forEach {
		val parts = it.split(SPLITTER)
		if (parts.size >= 2) add(parts[0] to parts[1].toFloat())
	}
}
val labels = ArrayList<Pair<Float, String>>(500).apply {
	LABELS.readText().split('\n').forEach {
		val parts = it.split(SPLITTER)
		if (parts.size >= 2) add(parts[0].toFloat() to parts[1])
	}
}

inputs.forEachIndexed { i, pair -> 
	println("""$i: ${pair.first} -> ${labels.find { pair.second == it.first }?.second ?: "[ERROR: NO ASSOCIATED LABEL]"}""")
}

println("\n\nType :quit to quit")
print("input: ")

var lastRead = ""
while (readLine()?.trim()?.also { lastRead = it } != ":quit") {
	print("label (empty -> skip): ")
	val label = readLine() ?: continue

	val index: Float = labels.find { it.second.equals(label, true) }?.first ?: (floor(labels.maxByOrNull { it.first }?.first ?: 0f) + 1f)
	inputs.add(lastRead to index)
	labels.add(index to label)

	println()
	print("input: ")
}

println()
println(inputs.joinToString(",  "))
println(labels.joinToString(",  "))

println("creating backup files...")
INPUTS.resolveSibling("inputs-backup.txt").writeBytes(INPUTS.readBytes())
LABELS.resolveSibling("labels-backup.txt").writeBytes(LABELS.readBytes())

println("overriding the original files...")

PrintWriter(INPUTS.outputStream()).use { writer ->
	inputs.forEach {
		writer.print(it.first)
		writer.print(SPLITTER)
		writer.println(it.second)
	}
}

PrintWriter(LABELS.outputStream()).use { writer ->
	labels.forEach {
		writer.print(it.first)
		writer.print(SPLITTER)
		writer.println(it.second)
	}
}
