plugins {
    id("com.github.johnrengelman.shadow") version ("7.0.0")
    id("net.minecrell.plugin-yml.bukkit") version ("0.5.1")
}

dependencies {
    api(project(":playersettings-api"))

    // Required libraries
    compileOnly(libs.annotations)
    compileOnly(libs.spigot)

    // Optional libraries
    implementation("team.unnamed:gui-menu-api:3.4.0-SNAPSHOT")
    implementation("team.unnamed:gui-menu-adapt-v1_19_R1:3.4.0-20221007.211619-1")

    implementation("team.unnamed:gui-item-api:3.4.0-SNAPSHOT")

    implementation(libs.hikari)
 // Optional plugin hooks
    //compileOnly("me.clip:placeholderapi:2.10.10")
}

bukkit {
    main = "team.emptyte.playersettings.plugin.PlayerSettingsPlugin"
    name = "playersettings"
    version = project.version.toString()
    apiVersion = "1.13"
    description = "Emptyte Team's Player Settings Plugin"
    author = "SrVenient"

    commands {
        create("settings") {
            description = "Main command for the player settings plugin"
            usage = "/<command> menu"
        }
    }
}

tasks {
    test {
        useJUnitPlatform()
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    shadowJar {
        from(project.sourceSets.main.get().output)

        // relocate libraries
        // TODO: Remove when creative-manage is ready
        val pkg = "team.emptyte.playersettings.lib"
        /*relocate("team.unnamed.creative", "$pkg.creative")
        relocate("net.kyori.examination", "$pkg.examination")
        relocate("net.kyori.adventure.key", "$pkg.adventure.key")*/
    }
}