plugins {
    kotlin("jvm") version "2.1.20"
    id("com.gradleup.shadow") version "8.3.1"
    application
}

group = "dev.dominic.c3po"
version = "1.0"

val jdaVersion = "5.6.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("ch.qos.logback:logback-classic:1.5.6")

    implementation ("net.dv8tion:JDA:${jdaVersion}")

    implementation("org.json:json:20250517")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

application.mainClass = "dev.dominic.c3po.BotKt"