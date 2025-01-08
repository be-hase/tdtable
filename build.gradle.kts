plugins {
    kotlin("multiplatform") version "2.1.0"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.2"
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
                implementation("com.squareup.okio:okio:3.10.2")
                implementation("org.jetbrains.kotlinx:kotlinx-html:0.11.0")
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
