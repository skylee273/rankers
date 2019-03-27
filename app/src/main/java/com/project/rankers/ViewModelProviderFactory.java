package com.project.rankers;



import com.project.rankers.ui.competition.CompetitionInfoViewModel;
import com.project.rankers.ui.login.LoginViewModel;
import com.project.rankers.ui.main.MainViewModel;
import com.project.rankers.ui.operation.ContestOperationViewModel;
import com.project.rankers.ui.personal.writing.WritingPersonalRecordViewModel;
import com.project.rankers.ui.register.ContestRegisterViewModel;
import com.project.rankers.ui.splash.SplashViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


@Singleton
public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {


  @Override
  public <T extends ViewModel> T create(Class<T> modelClass) {
    if (modelClass.isAssignableFrom(LoginViewModel.class)) {
      //noinspection unchecked
      return (T) new LoginViewModel();
    }else if (modelClass.isAssignableFrom(MainViewModel.class)){
      //noinspection unchecked
      return (T) new MainViewModel();
    }else if (modelClass.isAssignableFrom(SplashViewModel.class)){
      //noinspection unchecked
      return (T) new SplashViewModel();
    }else if (modelClass.isAssignableFrom(WritingPersonalRecordViewModel.class)){
      //noinspection unchecked
      return (T) new WritingPersonalRecordViewModel();
    }else if (modelClass.isAssignableFrom(ContestOperationViewModel.class)){
      //noinspection unchecked
      return (T) new ContestOperationViewModel();
    }else if (modelClass.isAssignableFrom(CompetitionInfoViewModel.class)){
      //noinspection unchecked
      return (T) new CompetitionInfoViewModel();
    } else if (modelClass.isAssignableFrom(ContestRegisterViewModel.class)){
      //noinspection unchecked
      return (T) new ContestRegisterViewModel();
    }

    throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
  }
}