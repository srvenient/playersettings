plugins {
    java
    id("com.github.johnrengelman.shadow") version ("7.0.0")
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "com.github.johnrengelman.shadow")

    configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    repositories {
        mavenCentral()
        mavenLocal()

        maven("https://repo.unnamed.team/repository/unnamed-public/")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.codemc.io/repository/nms/")
    }

    tasks {
        compileJava {
            options.compilerArgs.add("-parameters")
        }
    }
}