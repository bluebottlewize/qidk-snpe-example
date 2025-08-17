plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "org.bluebottlewize.qidk_snpe_demo"
    compileSdk = 35

    defaultConfig {
        applicationId = "org.bluebottlewize.qidk_snpe_demo"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    aaptOptions {
        noCompress("dlc")
    }
    ndkVersion = "21.4.7075529"
    buildToolsVersion = "34.0.0"
}

dependencies {

    implementation(files("libs/snpe-release.aar"))

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("commons-io:commons-io:2.16.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}