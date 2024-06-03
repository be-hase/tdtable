plugins {
    kotlin("multiplatform") version "2.0.0"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
}

kotlin {
    macosArm64 {
        binaries { executable() }
    }
    sourceSets {
        commonMain {
            dependencies {
                implementation("com.squareup.okio:okio:3.9.0")
                implementation("org.jetbrains.kotlinx:kotlinx-html:0.11.0")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
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
