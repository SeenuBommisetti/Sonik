package com.seenubommisetti.sonik.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.seenubommisetti.sonik.database.SonikDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<SqlDriver> { AndroidSqliteDriver(SonikDatabase.Schema, get(), "sonik.db") }
}
