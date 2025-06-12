import com.google.devtools.ksp.gradle.KspTaskMetadata

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.serialization)
}

repositories {
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/") // new repository here
}

kotlin {
    jvm()
    js(IR) {
        browser()
    }.binaries.executable()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.fritz2.core)
                implementation(libs.fritz2.serizalization)
            }
        }
        jvmMain {
            dependencies {
            }
        }
        jsMain {
            dependencies {
            }
        }
    }
}

// KSP support for Lens generation
dependencies.kspCommonMainMetadata(libs.fritz2.lenses)
kotlin.sourceSets.commonMain { tasks.withType<KspTaskMetadata> { kotlin.srcDir(destinationDirectory) } }
