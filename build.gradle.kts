plugins {
    kotlin("jvm") version "1.7.20"
}

group = "de.emreak.adventofcode"
version = "2022"

fun getValue(key: String, filename: String = "keys.properties"): String {
    val items = HashMap<String, String>()
    val f = File(filename)

    f.forEachLine {
        val split = it.split("=")
        items[split[0].trim()] = split[1].trim().removeSurrounding("\"")
    }

    return items[key]?: throw IllegalArgumentException("Key $key not found")
}

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

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.slf4j:slf4j-api:2.0.3")
    implementation("ch.qos.logback:logback-classic:1.4.4")
    implementation("ch.qos.logback:logback-core:1.4.4")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")
    implementation("tr.emreone:kotlin-utils:0.0.1")
    testImplementation(kotlin("test"))
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
        gradleVersion = "7.5.1"
    }
}

tasks.register("prepareNextDay") {
    doLast {
        val nextDay = 2
        val withTest = true
        val packageIdPath = "de.emreak.adventofcode".replace(".", "/")

        val mainFile    = """$projectDir/src/main/kotlin/${packageIdPath}/Main.kt"""
        val readmeFile  = """$projectDir/README.md"""
        val newSrcFile  = """$projectDir/src/main/kotlin/${packageIdPath}/days/Day${nextDay}.kt"""
        val newTestFile = """$projectDir/src/test/kotlin/${packageIdPath}/days/Day${nextDay}Test.kt"""

        if (file(newSrcFile).exists()) {
            println("WARNING: Files for Day$nextDay already exists. Do you really want to overwrite it?")
        } else {
            file(newSrcFile).writeText(
                file("""$projectDir/template/DayX.kt""")
                    .readText()
                    .replace("$1", "$nextDay")
            )
            file("""$projectDir/src/main/resources/day$nextDay.txt""").writeText("")

            file(mainFile).writeText(
                file(mainFile).readText()
                    .replace(
                        "// $1", """
                            |        $nextDay -> solveDay${nextDay}()
                            |       // ${"$1"} 
                        """.trimMargin()
                    )
                    .replace(
                        "// $2", """
                        fun solveDay${nextDay}() {
                            val input = FileLoader.readLines(filename = "day${nextDay}.txt")
    
                            val solution1 = Day${nextDay}.part1(input)
                            logger.info { "Solution1: ${"$"}solution1" }
    
                            val solution2 = Day${nextDay}.part2(input)
                            logger.info { "Solution2: ${"$"}solution2" }
                        }
                        
                        // ${"$2"}
                        """.trimIndent()
                    )
            )

            file(readmeFile).writeText(
                file(readmeFile).readText()
                    .replace(
                        "<!-- $1 -->", """
                            |[Day ${nextDay}](https://adventofcode.com/2022/day/${nextDay}) | [Day${nextDay}Test.kt](https://github.com/EmRe-One/advent-of-code-2022/blob/master/src/test/kotlin/de/emreak/adventofcode/days/Day${nextDay}Test.kt) | [Day${nextDay}.kt](https://github.com/EmRe-One/advent-of-code-2022/blob/master/src/main/kotlin/de/emreak/adventofcode/days/Day${nextDay}.kt) |       |       |
                            ${"<!-- $1 -->"}
                        """.trimIndent()
                    )
            )

            if (withTest) {
                file(newTestFile).writeText(
                    file("""$projectDir/template/DayXTest.kt""")
                        .readText()
                        .replace("$1", "$nextDay")
                )

                file("""$projectDir/src/test/resources/day${nextDay}_example.txt""")
                    .writeText("")
            }
        }
    }
}