import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.implementation(dependency: Dependency) {
    add("implementation", create(dependency.notation))
}

fun DependencyHandlerScope.debugImplementation(dependency: Dependency) {
    add("debugImplementation", create(dependency.notation))
}

fun DependencyHandlerScope.testImplementation(dependency: Dependency) {
    add("testImplementation", create(dependency.notation))
}

fun DependencyHandlerScope.androidTestImplementation(dependency: Dependency) {
    add("androidTestImplementation", create(dependency.notation))
}

fun DependencyHandlerScope.ksp(dependency: Dependency) {
    add("ksp", create(dependency.notation))
}

sealed class Dependency(val notation: String) {
    sealed class Core(notation: String) : Dependency(notation) {
        object Ktx : Core("androidx.core:core-ktx:1.8.0")
        object AppCompat : Core("androidx.appcompat:appcompat:1.5.0")
    }
    sealed class Compose(notation: String) : Dependency(notation) {
        object Ui : Compose("androidx.compose.ui:ui:${Versions.Compose}")
        object UiTestJunit4 : Compose("androidx.compose.ui:ui-test-junit4:${Versions.Compose}")
        object Tooling : Compose("androidx.compose.ui:ui-tooling:${Versions.Compose}")
        object ToolingPreview : Compose("androidx.compose.ui:ui-tooling-preview:${Versions.Compose}")
        object Activity : Compose("androidx.activity:activity-compose:${Versions.Activity}")
        object Material3 : Compose("androidx.compose.material3:material3:1.0.0-alpha16")
        object FlowLayout : Compose("com.google.accompanist:accompanist-flowlayout:${Versions.Accompanist}")
    }
    sealed class Lifecycle(notation: String) : Dependency(notation) {
        object RuntimeKtx : Lifecycle("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Lifecycle}")
    }
    object Junit4 : Dependency("junit:junit:4.13.2")
    sealed class Koin(notation: String) : Dependency(notation) {
        object Core : Koin("io.insert-koin:koin-core:${Versions.Koin}")
        object Android : Koin("io.insert-koin:koin-android:${Versions.Koin}")
        object Annotations : Koin("io.insert-koin:koin-annotations:${Versions.KoinAnnotations}")
        object Compiler : Koin("io.insert-koin:koin-ksp-compiler:${Versions.KoinAnnotations}")
    }
    sealed class Ktor(notation: String) : Dependency(notation) {
        object Core : Ktor("io.ktor:ktor-client-core:${Versions.Ktor}")
        object OkHttp : Ktor("io.ktor:ktor-client-okhttp:${Versions.Ktor}")
        object OkHttpLogging : Ktor("com.squareup.okhttp3:logging-interceptor:4.10.0")
        object ContentNegotiation : Ktor("io.ktor:ktor-client-content-negotiation:${Versions.Ktor}")
        object Gson : Ktor("io.ktor:ktor-serialization-gson:${Versions.Ktor}")
        object Auth : Ktor("io.ktor:ktor-client-auth:${Versions.Ktor}")
    }
    sealed class Room(notation: String) : Dependency(notation) {
        object Core : Room("androidx.room:room-runtime:${Versions.Room}")
        object Ktx : Room("androidx.room:room-ktx:${Versions.Room}")
        object Compiler : Room("androidx.room:room-compiler:${Versions.Room}")
    }
    object Coil : Dependency("io.coil-kt:coil-compose:2.2.0")
    sealed class Navigation(notation: String) : Dependency(notation) {
        object Appyx : Navigation("com.bumble.appyx:core:1.0-alpha06")
    }
    sealed class VideoPlayer(notation: String) : Dependency(notation) {
        object Core : VideoPlayer("com.google.android.exoplayer:exoplayer:${Versions.ExoPlayer}")
        object Hls : VideoPlayer("com.google.android.exoplayer:exoplayer-hls:${Versions.ExoPlayer}")
    }
}
