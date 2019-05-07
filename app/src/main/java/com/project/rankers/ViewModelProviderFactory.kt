package com.project.rankers


import com.project.rankers.ui.competition.CompetitionInfoViewModel
import com.project.rankers.ui.form.ApplicationFormViewModel
import com.project.rankers.ui.login.LoginViewModel
import com.project.rankers.ui.main.MainViewModel
import com.project.rankers.ui.operation.ContestOperationViewModel
import com.project.rankers.ui.contestRegister.ContestRegisterViewModel
import com.project.rankers.ui.splash.SplashViewModel

import javax.inject.Singleton

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.rankers.ui.apply.ApplyViewModel
import com.project.rankers.ui.league.LeagueViewModel
import com.project.rankers.ui.leagueResult.LeagueResultViewModel
import com.project.rankers.ui.main.contest.ContestViewModel
import com.project.rankers.ui.match.MatchViewModel
import com.project.rankers.ui.operator.OperatorViewModel
import com.project.rankers.ui.record.RecordViewModel
import com.project.rankers.ui.record.multi.MultiViewModel
import com.project.rankers.ui.record.single.SingleViewModel
import com.project.rankers.ui.register.RegisterViewModel


@Singleton
class ViewModelProviderFactory : ViewModelProvider.NewInstanceFactory() {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel() as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel() as T
        } else if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel() as T
        }  else if (modelClass.isAssignableFrom(ContestOperationViewModel::class.java)) {
            return ContestOperationViewModel() as T
        } else if (modelClass.isAssignableFrom(CompetitionInfoViewModel::class.java)) {
            return CompetitionInfoViewModel() as T
        } else if (modelClass.isAssignableFrom(ContestRegisterViewModel::class.java)) {
            return ContestRegisterViewModel() as T
        } else if (modelClass.isAssignableFrom(ApplicationFormViewModel::class.java)) {
            return ApplicationFormViewModel() as T
        }else if (modelClass.isAssignableFrom(OperatorViewModel::class.java)) {
            return OperatorViewModel() as T
        }else if (modelClass.isAssignableFrom(ContestViewModel::class.java)) {
            return ContestViewModel() as T
        } else if (modelClass.isAssignableFrom(LeagueResultViewModel::class.java)) {
            return LeagueResultViewModel() as T
        } else if (modelClass.isAssignableFrom(ApplyViewModel::class.java)) {
            return ApplyViewModel() as T
        } else if (modelClass.isAssignableFrom(LeagueViewModel::class.java)) {
            return LeagueViewModel() as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel() as T
        } else if (modelClass.isAssignableFrom(MatchViewModel::class.java)) {
            return MatchViewModel() as T
        }else if (modelClass.isAssignableFrom(RecordViewModel::class.java)) {
            return RecordViewModel() as T
        }else if (modelClass.isAssignableFrom(MultiViewModel::class.java)) {
            return MultiViewModel() as T
        }else if (modelClass.isAssignableFrom(SingleViewModel::class.java)) {
            return SingleViewModel() as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}