plugins {
    id("com.github.johnrengelman.shadow") version ("7.0.0")
}

dependencies {
    api(project(":api"))

    compileOnly("org.spigotmc:spigot:1.19.2-R0.1-SNAPSHOT")

    implementation("team.unnamed:gui-menu-api:3.4.0-SNAPSHOT")
    implementation("team.unnamed:gui-menu-adapt-v1_19_R1:3.4.0-20221007.211619-1")

    implementation("team.unnamed:gui-item-api:3.4.0-SNAPSHOT")
}

tasks {
    shadowJar {
        archiveBaseName.set("playersettings")
        archiveClassifier.set("java${project.property("java")}")
    }

    processResources {
        filesMatching("**/*.yml") {
            filter<org.apache.tools.ant.filters.ReplaceTokens>(
                    "tokens" to mapOf("version" to project.version)
            )
        }
    }
}