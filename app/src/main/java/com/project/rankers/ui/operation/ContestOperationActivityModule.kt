package com.project.rankers.ui.operation

import dagger.Module
import dagger.Provides

@Module
class ContestOperationActivityModule {
    @Provides
    fun provideContestPagerAdapter(activity : ContestOperationActivity): ContestOperationPagerAdapter {
        return ContestOperationPagerAdapter(activity.supportFragmentManager)
    }
}