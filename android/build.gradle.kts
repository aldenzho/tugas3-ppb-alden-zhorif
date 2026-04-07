allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://repo1.maven.org/maven2") }
        maven { url = uri("https://maven.aliyun.com/repository/public") }
    }
}

val newBuildDir: Directory =
    rootProject.layout.buildDirectory
        .dir("../../build")
        .get()
rootProject.layout.buildDirectory.value(newBuildDir)

subprojects {
    afterEvaluate {
        if (plugins.hasPlugin("com.android.application") ||
                plugins.hasPlugin("com.android.library")) {
            extensions.configure<com.android.build.gradle.BaseExtension> {
                compileSdkVersion(36)
                buildToolsVersion = "36.0.0"
            }
        }
        if (hasProperty("android")) {
            extensions.configure<com.android.build.gradle.BaseExtension> {
                if (namespace == null) {
                    namespace = group.toString()
                }
            }
        }
    }
    val newSubprojectBuildDir: Directory = newBuildDir.dir(name)
    layout.buildDirectory.value(newSubprojectBuildDir)
    evaluationDependsOn(":app")
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
