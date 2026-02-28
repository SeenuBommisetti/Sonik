package com.seenubommisetti.sonik.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

import org.koin.core.module.Module

expect fun platformModule(): Module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(appModule, platformModule())
    }
}
