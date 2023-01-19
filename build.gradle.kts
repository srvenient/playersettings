plugins {
    java
}

subprojects {
    apply(plugin = "java-library")

    tasks {
        java {
            toolchain {
                languageVersion.set(
                    JavaLanguageVersion.of(
                        project.property("java").toString()
                    )
                )
            }
        }
    }

    repositories {
        mavenCentral()

        maven("https://repo.unnamed.team/repository/unnamed-public/")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.codemc.io/repository/nms/")

        mavenLocal()
    }
}