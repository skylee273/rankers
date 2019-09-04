package com.project.rankers.ui.version

import androidx.databinding.ObservableField
import com.project.rankers.data.remote.api.Api
import com.project.rankers.ui.base.BaseViewModel
import java.util.*

class VersionViewModel : BaseViewModel<VersionNavigator>() {
    var version : ObservableField<String>

    init {
        version = ObservableField("")
    }
    fun fetchVersion(){
        //setIsLoading(true)
        // Versrion Check
        version.set("현재 버전은 1.0.0 입니다.")
    }
}