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
import com.github.mnemotechnician.botdl.dataset.*

class Learner(
	var epochs: Int = 20,
	var batchSize: Int = 5000
) {
	fun learn() {
		val (train, test) = PhraseDataset.split(0.8f)

		net.use {
			it.compile(
				optimizer = Adam(),
				loss = Losses.SOFT_MAX_CROSS_ENTROPY_WITH_LOGITS,
				metric = Metrics.ACCURACY
			)

			it.logSummary()

			val start = System.currentTimeMillis()
			it.fit(dataset = train, epochs = EPOCHS, batchSize = TRAINING_BATCH_SIZE)
			println("Training time: ${(System.currentTimeMillis() - start) / 1000f}")

      			it.save(File(PATH_TO_MODEL), writingMode = WritingMode.OVERRIDE)

			val accuracy = it.evaluate(dataset = test, batchSize = TEST_BATCH_SIZE).metrics[Metrics.ACCURACY]

			println("Accuracy: $accuracy")
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
