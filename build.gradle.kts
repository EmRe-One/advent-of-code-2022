plugins {
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"
    application
}

group = "tr.emreone.adventofcode"
version = "2022"

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/Emre-One/kotlin-utils")
        credentials {
            username = getValue("GITHUB_USERNAME")
            password = getValue("GITHUB_TOKEN")
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    // Do not use, no effect; will be overridden by kotlinDslPluginOptions.jvmTarget, see KotlinDslCompilerPlugins.
    kotlinOptions.jvmTarget = JavaVersion.VERSION_19.toString()
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src/main/kotlin")
        }
    }
    test {
        useJUnitPlatform()
    }
    wrapper {
        gradleVersion = "8.5"
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation("ch.qos.logback:logback-core:1.4.5")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.mariuszgromada.math:MathParser.org-mXparser:5.1.0")

    implementation("org.reflections:reflections:0.10.2")
    implementation("com.github.ajalt.mordant:mordant:2.2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    implementation("tr.emreone:kotlin-utils:0.3.4")

    testImplementation(kotlin("test"))
}

/**
 *
 */
fun getValue(key: String, filename: String = "../keys.properties"): String {
    val items = HashMap<String, String>()
    val f = File(filename)

    f.forEachLine {
        val split = it.split("=")
        items[split[0].trim()] = split[1].trim().removeSurrounding("\"")
    }

    return items[key] ?: throw IllegalArgumentException("Key $key not found")
}
