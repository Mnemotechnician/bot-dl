package com.github.mnemotechnician.botdl

val INPUT_LENGTH = 300

fun main() {
	println("select the mode. 1 = train, 2 = run")

	when (readLine()?.trim()?.toIntOrNull()) {
		1 -> Learner().learn()
		2 -> println("this is not implemented yet!")

		else -> println("you should choose either 1 or 2")
	}
}
