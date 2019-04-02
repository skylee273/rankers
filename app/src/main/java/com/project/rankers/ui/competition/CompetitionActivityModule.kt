package com.project.rankers.ui.competition

import com.project.rankers.data.remote.response.ContestResponse
import dagger.Module
import dagger.Provides

@Module
class CompetitionActivityModule {

    @Provides
    internal fun provideCompetitionAdapter(): CompetitionAdapter {
        return CompetitionAdapter(ArrayList<ContestResponse.Repo>())
    }
}