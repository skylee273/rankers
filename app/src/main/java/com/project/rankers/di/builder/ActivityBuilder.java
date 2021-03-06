/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.project.rankers.di.builder;


import com.project.rankers.ui.contest.competition.CompetitionActivityModule;
import com.project.rankers.ui.contest.competition.CompetitionInfoActivity;
import com.project.rankers.ui.contest.competition.CompetitionActivityProvider;
import com.project.rankers.ui.contest.leagueResult.LeagueResultActivity;
import com.project.rankers.ui.login.LoginActivity;
import com.project.rankers.ui.main.MainActivity;
import com.project.rankers.ui.contest.operation.ContestOperationActivity;
import com.project.rankers.ui.contest.operation.ContestOperationActivityModule;
import com.project.rankers.ui.contest.operator.OperatorActivity;
import com.project.rankers.ui.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by amitshekhar on 14/09/17.
 */
@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {
            CompetitionActivityModule.class,
            CompetitionActivityProvider.class
    })
    abstract CompetitionInfoActivity bindCompetitionInfoActivity();

    @ContributesAndroidInjector
    abstract LeagueResultActivity bindLeagueResultActivity();
    @ContributesAndroidInjector
    abstract OperatorActivity bindOperatorActivityActivity();

    @ContributesAndroidInjector(modules = {
            ContestOperationActivityModule.class})
    abstract ContestOperationActivity bindContestOperativeActivity();

    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();
}
