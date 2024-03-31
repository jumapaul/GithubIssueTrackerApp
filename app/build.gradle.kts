plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    id("com.google.gms.google-services")
    id("com.apollographql.apollo3")
}

apollo {
    generateKotlinModels.set(true)
    packageNamesFromFilePaths()
}

android {
    namespace = "com.example.githubtracker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.githubtracker"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Runtime
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)

    //hilt
    implementation(libs.hilt.android)
    ksp(libs.google.hilt.compiler)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation)

    //Accompanist
    implementation(libs.navigation.compose)
    implementation(libs.viewmodel.compose)

    //Accompanist
    implementation(libs.accompanist.pager)
    implementation(libs.lottie.compose)
    implementation(libs.accompanist.pager.indicators)

    //Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room)

    //material extended
    implementation(libs.material.extended)

    //DataStore
    implementation(libs.datastore.preferences)

    //gson
    implementation(libs.gson)

    //glide
    implementation(libs.coil.compose)

    //apollo
    implementation(libs.apollo.runtime)
    //okhttp logging
    implementation(libs.logging.interceptor)
}