import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting  {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":shared"))
            }
            configurations.all {
                // some dependencies contains it, this causes an exception to initialize the Main dispatcher in desktop for image loader
                exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-android")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "1SecMail App"
            packageVersion = "1.0.0"
        }
    }
}
