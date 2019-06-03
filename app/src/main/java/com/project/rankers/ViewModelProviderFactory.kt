package com.project.rankers


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.rankers.ui.board.BoardViewModel
import com.project.rankers.ui.contest.apply.ApplyViewModel
import com.project.rankers.ui.contest.competition.CompetitionInfoViewModel
import com.project.rankers.ui.contest.contestRegister.ContestRegisterViewModel
import com.project.rankers.ui.contest.contestResult.ContestResultViewModel
import com.project.rankers.ui.contest.contestResultLeague.ContestResultLeagueViewModel
import com.project.rankers.ui.contest.contestResultTournament.ContestResultTournamentViewModel
import com.project.rankers.ui.contest.form.ApplicationFormViewModel
import com.project.rankers.ui.contest.league.LeagueViewModel
import com.project.rankers.ui.contest.leagueResult.LeagueResultViewModel
import com.project.rankers.ui.contest.operation.ContestOperationViewModel
import com.project.rankers.ui.contest.operation.dashboard.DashBoardViewModel
import com.project.rankers.ui.contest.operator.OperatorViewModel
import com.project.rankers.ui.login.LoginViewModel
import com.project.rankers.ui.main.MainViewModel
import com.project.rankers.ui.main.contest.ContestViewModel
import com.project.rankers.ui.main.rank.RankViewModel
import com.project.rankers.ui.match.MatchViewModel
import com.project.rankers.ui.record.RecordViewModel
import com.project.rankers.ui.record.multi.MultiViewModel
import com.project.rankers.ui.record.single.SingleViewModel
import com.project.rankers.ui.register.RegisterViewModel
import com.project.rankers.ui.reply.ReplyViewModel
import com.project.rankers.ui.splash.SplashViewModel
import javax.inject.Singleton


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
        }else if (modelClass.isAssignableFrom(ContestResultViewModel::class.java)) {
            return ContestResultViewModel() as T
        }else if (modelClass.isAssignableFrom(ContestResultLeagueViewModel::class.java)) {
            return ContestResultLeagueViewModel() as T
        }else if (modelClass.isAssignableFrom(ContestResultTournamentViewModel::class.java)) {
            return ContestResultTournamentViewModel() as T
        }else if (modelClass.isAssignableFrom(DashBoardViewModel::class.java)) {
            return DashBoardViewModel() as T
        }else if (modelClass.isAssignableFrom(RankViewModel::class.java)) {
            return RankViewModel() as T
        }else if (modelClass.isAssignableFrom(BoardViewModel::class.java)) {
            return BoardViewModel() as T
        }else if (modelClass.isAssignableFrom(ReplyViewModel::class.java)) {
            return ReplyViewModel() as T
        }





        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}