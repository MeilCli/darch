package net.meilcli.darch.gradle

object Libraries {

    object Kotlin {

        private const val kotlinVersion = "1.3.61"
        private const val coroutineVersion = "1.3.3"

        val gradle = Dependency("org.jetbrains.kotlin", "kotlin-gradle-plugin", kotlinVersion)
        val stdlib = Dependency("org.jetbrains.kotlin", "kotlin-stdlib-jdk7", kotlinVersion)
        val reflect = Dependency("org.jetbrains.kotlin", "kotlin-reflect", kotlinVersion)
        val coroutineCore = Dependency(
            "org.jetbrains.kotlinx",
            "kotlinx-coroutines-core",
            coroutineVersion
        )
        val coroutineAndroid = Dependency(
            "org.jetbrains.kotlinx",
            "kotlinx-coroutines-android",
            coroutineVersion
        )
    }

    object Android {

        val gradle = Dependency("com.android.tools.build", "gradle", "3.5.3")
        val junit = Dependency("androidx.test.ext", "junit", "1.1.1")
        val espresso = Dependency("androidx.test.espresso", "espresso-core", "3.2.0")
    }

    object Junit {

        val junit4 = Dependency("junit", "junit", "4.12")
    }

    object Koin {

        private const val koinVersion = "2.1.1"

        val gradle = Dependency("org.koin", "koin-gradle-plugin", koinVersion)
        val core = Dependency("org.koin", "koin-core", koinVersion)
        val coreExtension = Dependency("org.koin", "koin-core-ext", koinVersion)
        val android = Dependency("org.koin", "koin-android", koinVersion)
    }
}