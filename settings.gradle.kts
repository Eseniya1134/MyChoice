pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MyChoiceApplication"
include(":app")
include(":core")
include(":feature")
include(":core:network")
include(":core:database")
include(":feature:auth")
include(":feature:auth:domain")
include(":feature:auth:presentation")
include(":feature:auth:data")
include(":feature:auth:di")
include(":feature:profile")
include(":feature:settings")
include(":feature:profile:data")
include(":feature:profile:di")
include(":feature:profile:domain")
include(":feature:profile:presentation")
include(":feature:settings:data")
include(":feature:settings:di")
include(":feature:settings:domain")
include(":feature:settings:presentation")
include(":app:presentation")
include(":core:ui-kit")
include(":core:resources")
include(":feature:search")
