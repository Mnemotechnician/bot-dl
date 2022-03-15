plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    application
}

repositories {
    mavenCentral()
}

val dlVersion = "0.4.0-alpha-2"

dependencies {
	implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	
	implementation("org.tensorflow:libtensorflow:1.15.0")
 	implementation("org.jetbrains.kotlinx:kotlin-deeplearning-api:$dlVersion")
	implementation("org.jetbrains.kotlinx:kotlin-deeplearning-onnx:$dlVersion")
}

application {
    mainClass.set("com.github.mnemotechnician.botdl.AppKt")
}

tasks.jar {
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
	
	manifest {
		attributes["Main-Class"] = "com.github.mnemotechnician.botdl.AppKt"
	}
	
	from(*configurations.runtimeClasspath.files.map { if (it.isDirectory()) it else zipTree(it) }.toTypedArray())
}
