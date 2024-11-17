plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.grampanchayatkouthaliapk"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.grampanchayatkouthaliapk"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }
}


dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.8.3")
    implementation("androidx.navigation:navigation-ui:2.8.0")
    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation( "com.google.android.material:material:1.7.0")
    implementation("com.google.android.material:material:1.8.0")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation ("com.google.firebase:firebase-auth:21.0.1")
    implementation ("com.google.firebase:firebase-storage:20.0.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation("androidx.preference:preference:1.2.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation ("com.google.firebase:firebase-firestore:23.0.3")
    // Firebase Realtime Database
    implementation(platform("com.google.firebase:firebase-bom:33.3.0"))
    implementation("com.google.firebase:firebase-database:20.3.0") // Add Realtime Database dependency
    implementation("com.google.firebase:firebase-auth:21.0.1") // Authentication if needed
    implementation("com.google.android.gms:play-services-auth:19.0.0") // Google sign-in
    implementation ("com.google.firebase:firebase-firestore:24.0.2")
            implementation ("com.google.firebase:firebase-database:20.2.6")

    implementation ("com.sun.mail:android-mail:1.6.5")
    implementation ("com.sun.mail:android-activation:1.6.5")

    implementation ("com.google.firebase:firebase-database:20.0.5")
    implementation ("com.google.firebase:firebase-auth:21.0.1")  // Required for authentication if needed

    implementation ("com.google.firebase:firebase-database:20.1.0")
    implementation ("com.google.firebase:firebase-storage:20.1.0")

    implementation ("com.github.bumptech.glide:glide:4.12.0")


    // Image loading library
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.google.firebase:firebase-storage:21.0.1")


    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")

}

