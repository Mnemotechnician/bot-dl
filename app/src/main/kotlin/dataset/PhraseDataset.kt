package com.github.mnemotechnician.botdl.dataset

import org.jetbrains.kotlinx.dl.dataset.*
import com.github.mnemotechnician.botdl.*

val outputMap = HashMap<Float, String>(500).let { map ->
	object{}::class.java.getResource("/labels.txt").readText().split('\n').forEach {
		val sep = it.indexOf("->")

		map.set(it.substring(0, sep).toFloat(), it.substring(sep + 2, it.length - 1))
	}
}

val PhraseDataset = Unit.let {
	val data = object {}::class.java.getResource("/inputs.txt").readText().split('\n')

	val input = Array(data.size) { FloatArray(INPUT_LENGTH) { 0f } }
	val output = FloatArray(data.size) { 0f }

	data.forEachIndexed { index, entry ->
		val sep = entry.indexOf("->")
		input[index] = entry.substring(0, sep).toDlString()
		output[index] = entry.substring(sep + 2, entry.length - 1).trim().toFloat()
	}

	OnHeapDataset.create(input, output)
}

fun String.toDlString() = let { string ->
	FloatArray(INPUT_LENGTH) { string.getOrNull(it)?.code?.toFloat() ?: 0f }
}
