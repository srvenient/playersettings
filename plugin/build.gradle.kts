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

tasks {
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