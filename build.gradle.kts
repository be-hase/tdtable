plugins {
    kotlin("multiplatform") version "2.3.0"
    id("org.jlleitschuh.gradle.ktlint") version "12.3.0"
}

kotlin {
    macosArm64 {
        binaries { executable() }
    }
    macosX64 {
        binaries { executable() }
    }
    linuxX64 {
        binaries { executable() }
    }
    linuxArm64 {
        binaries { executable() }
    }
    sourceSets {
        commonMain {
            dependencies {
                implementation("com.squareup.okio:okio:3.16.4")
                implementation("org.jetbrains.kotlinx:kotlinx-html:0.12.0")
                implementation("com.github.ajalt.clikt:clikt:4.4.0")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation("com.willowtreeapps.assertk:assertk:0.28.1")
            }
        }
    }
}

ktlint {
    verbose.set(true)
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.SARIF) // used for reviewdog
    }
}
