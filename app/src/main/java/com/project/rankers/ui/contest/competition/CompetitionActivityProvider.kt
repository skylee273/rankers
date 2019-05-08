package com.project.rankers.ui.contest.competition

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CompetitionActivityProvider {

    @ContributesAndroidInjector(modules = [CompetitionActivityModule::class])
    internal abstract fun provideCompetitionActivityFactory(): CompetitionInfoActivity
}