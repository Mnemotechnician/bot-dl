package com.github.mnemotechnician.botdl

import org.jetbrains.kotlinx.dl.api.core.*
import org.jetbrains.kotlinx.dl.api.core.activation.*
import org.jetbrains.kotlinx.dl.api.core.callback.*
import org.jetbrains.kotlinx.dl.api.core.history.*
import org.jetbrains.kotlinx.dl.api.core.initializer.*
import org.jetbrains.kotlinx.dl.api.core.layer.convolutional.*
import org.jetbrains.kotlinx.dl.api.core.layer.core.*
import org.jetbrains.kotlinx.dl.api.core.layer.pooling.*
import org.jetbrains.kotlinx.dl.api.core.layer.reshaping.*
import org.jetbrains.kotlinx.dl.api.core.loss.*
import org.jetbrains.kotlinx.dl.api.core.optimizer.*
import org.jetbrains.kotlinx.dl.api.core.metric.*

class Learner(
	var epochs: Int = 20,
	var batchSize: Int = 5000
) {
	fun learn() {
		net.use {
			it.compile(
				optimizer = Adam(),
				loss = Losses.SOFT_MAX_CROSS_ENTROPY_WITH_LOGITS,
				metric = Metrics.ACCURACY
			)
		}
	}

	companion object {
		val net = Sequential.of(
			Input(INPUT_LENGTH.toLong()),
			Dense(300),
			Dense(200),
			Dense(80)
		)
	}
}
