buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("java")
    id("maven-publish")
    id("com.github.johnrengelman.shadow").version("7.1.0")
    id("io.github.gradle-nexus.publish-plugin").version("1.1.0")
    // https://github.com/PaperMC/paperweight
//    id("io.papermc.paperweight.userdev").version("1.5.11")
}
group = "dev.ckateptb.minecraft"
version = "1.0.1-SNAPSHOT"

val rootPackage = "${project.group}.${project.name.toLowerCase().split('-')[0]}"
val internal = "${rootPackage}.internal"

repositories {
    mavenCentral()
    maven("https://repo.jyraf.com/repository/maven-snapshots/")
    maven("https://repo.codemc.io/repository/nms/")
}

dependencies {
    // paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
    compileOnly("com.destroystokyo.paper:paper:1.16.5-R0.1-SNAPSHOT")

    compileOnly("dev.ckateptb.minecraft:Jyraf-Core:1.21.0-SNAPSHOT")

    compileOnly("org.projectlombok:lombok:+")
    annotationProcessor("org.projectlombok:lombok:+")
}

tasks {
    shadowJar {
        archiveClassifier.set("")
    }
    build {
        dependsOn(/*reobfJar, */shadowJar)
    }
    publish {
        dependsOn(/*reobfJar, */shadowJar)
    }
    withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(16)
    }
    named<Copy>("processResources") {
        filesMatching("plugin.yml") {
            expand(
                    "projectVersion" to project.version,
                    "projectName" to project.name,
                    "projectMainClass" to "${rootPackage}.${project.name.split('-')[0]}"
            )
        }
        from("LICENSE") {
            rename { "${project.name.toUpperCase()}_${it}" }
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(16))
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(tasks.getByName("shadowJar").outputs.files.singleFile)
        }
    }
}

nexusPublishing {
    repositories {
        create("jyrafRepo") {
            nexusUrl.set(uri("https://repo.jyraf.com/"))
            snapshotRepositoryUrl.set(uri("https://repo.jyraf.com/repository/maven-snapshots/"))
            username.set(System.getenv("NEXUS_USERNAME"))
            password.set(System.getenv("NEXUS_PASSWORD"))
        }
    }
}
