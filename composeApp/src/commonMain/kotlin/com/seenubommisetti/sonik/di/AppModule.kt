package com.seenubommisetti.sonik.di

import com.seenubommisetti.sonik.database.SonikDatabase
import com.seenubommisetti.sonik.network.MusicRepository
import com.seenubommisetti.sonik.player.AudioPlayer
import com.seenubommisetti.sonik.ui.MusicViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule: Module = module {
    single { SonikDatabase(get()) }
    singleOf(::MusicRepository)
    singleOf(::AudioPlayer)
    viewModelOf(::MusicViewModel)
}
