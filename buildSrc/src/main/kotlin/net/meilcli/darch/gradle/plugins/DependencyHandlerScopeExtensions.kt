package net.meilcli.darch.gradle.plugins

import net.meilcli.darch.gradle.Dependency
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.create

fun DependencyHandlerScope.implementation(dependency: Dependency) {
    add(
        "implementation",
        create(
            group = dependency.group,
            name = dependency.name,
            version = dependency.version
        )
    )
}

fun DependencyHandlerScope.testImplementation(dependency: Dependency) {
    add(
        "testImplementation",
        create(
            group = dependency.group,
            name = dependency.name,
            version = dependency.version
        )
    )
}

fun DependencyHandlerScope.androidTestImplementation(dependency: Dependency) {
    add(
        "androidTestImplementation",
        create(
            group = dependency.group,
            name = dependency.name,
            version = dependency.version
        )
    )
}