package com.seenubommisetti.sonik.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.seenubommisetti.sonik.database.SonikDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<SqlDriver> { 
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        SonikDatabase.Schema.create(driver)
        driver
    }
}
