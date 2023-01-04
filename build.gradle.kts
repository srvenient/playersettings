plugins { java }

subprojects {
    apply(plugin = "java-library")

    tasks {
        java {
            toolchain {
                languageVersion.set(
                        JavaLanguageVersion.of(17)
                )
            }
        }
    }

    repositories {
        mavenLocal()
        maven("https://repo.unnamed.team/repository/unnamed-public/")
        maven("https://repo.codemc.io/repository/nms/")
        maven("https://libraries.minecraft.net/")
        mavenCentral()
    }
}