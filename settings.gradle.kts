rootProject.name = "playersettings"

includePrefixed("api")
includePrefixed("plugin")

fun includePrefixed(name: String) {
    include("playersettings-$name")
    project(":playersettings-$name").projectDir = file(name)
}
