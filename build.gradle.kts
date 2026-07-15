import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("net.fabricmc.fabric-loom-remap") version "1.17.14"
    kotlin("jvm") version "2.1.20"
    id("maven-publish")
}

val minecraftVersion = stonecutter.current.project
val loaderVersion = property("loader_version") as String
val fabricApiVersion = property("fabric_api_version") as String
val javaVersion = (property("java_version") as String).toInt()

// Kotlin can only emit bytecode up to JVM 21 today; on newer JDKs we still
// target 21 so the same sources build on the 26.x (Java 25) modules.
val kotlinTarget = minOf(javaVersion, 21)

version = property("mod_version")!!
group = property("maven_group")!!

base {
    archivesName.set("serverunblocker-mc-${minecraftVersion}")
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricApiVersion")
    // Fabric Language Kotlin provides the runtime adapter. It is declared as a
    // dependency in fabric.mod.json, so end users install it like Fabric API.
    // Uncomment for dev runs if your local FLK build matches this MC version:
    // modRuntimeOnly("net.fabricmc:fabric-language-kotlin:1.13.6+kotlin.2.1.20")
}

loom {
    runConfigs.all {
        ideConfigGenerated(true)
        runDir = "../../run"
    }
}

tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("minecraft_version", minecraftVersion)
    inputs.property("loader_version", loaderVersion)
    inputs.property("java_version", javaVersion)
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(
            "version" to project.version,
            "minecraft_version" to minecraftVersion,
            "loader_version" to loaderVersion,
            "java_version" to javaVersion
        )
    }
    filesMatching("serverunblocker.mixins.json") {
        expand("java_version" to javaVersion)
    }
}

kotlin {
    jvmToolchain(javaVersion)
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(kotlinTarget.toString()))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion))
    withSourcesJar()
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${base.archivesName.get()}" }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = base.archivesName.get()
            from(components["java"])
        }
    }
}
