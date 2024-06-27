plugins {
    kotlin("jvm") version "1.9.21"
    id("io.papermc.paperweight.userdev") version "1.7.1"
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
        archiveFileName.set("EuroCup24.jar")
    }

    assemble {
        dependsOn(reobfJar)
    }
}
