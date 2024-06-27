import java.util.*

plugins {
    kotlin("jvm") version "1.9.21"
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("xyz.jpenilla.run-paper") version "2.3.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "de.smoofy"
version = "1.0"

repositories {
    mavenCentral()
    mavenLocal()

    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")

    implementation(libs.evelon)

    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        sourceCompatibility = JavaVersion.VERSION_17.toString()
        targetCompatibility = JavaVersion.VERSION_17.toString()
    }

    shadowJar {
        archiveClassifier.set("")
    }

    assemble {
        dependsOn(reobfJar)
    }

    runServer {
        dependsOn("copyToServer")
        minecraftVersion("1.20.4")
    }

    register<Copy>("copyToServer") {
        val props = Properties()
        val propFile = file("build.properties")
        if (!propFile.exists()) propFile.createNewFile()
        file("build.properties").reader().let { props.load(it) }
        val path = props.getProperty("targetDir") ?: ""
        if (path.isEmpty()) throw RuntimeException("targetDir is not set in build.properties")
        from(jar)
        destinationDir = File(path)
    }
}
