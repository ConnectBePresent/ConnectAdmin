import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "1.9.22"
    id("org.jetbrains.compose") version "1.6.0"
    id("com.google.gms.google-services") version "4.4.1" apply false
}

group = "s8.project.connect"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("com.russhwolf:multiplatform-settings-no-arg:1.1.1")

    implementation("cafe.adriel.voyager:voyager-navigator:1.0.0")

    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.9.3")
    implementation("com.darkrockstudios:mpfilepicker:3.1.0")

    implementation("io.github.koalaplot:koalaplot-core:0.5.2")

    implementation("com.mohamedrejeb.calf:calf-ui:0.4.0")
}

compose.desktop {
    application {
        mainClass = "MainKt"

//      https://github.com/JetBrains/compose-multiplatform/blob/master/tutorials/Native_distributions_and_local_execution/README.md
        nativeDistributions {
//            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            targetFormats(TargetFormat.Dmg)
            includeAllModules = true
            packageName = "ConnectAdmin"
            packageVersion = "1.0.0"
        }
    }
}
