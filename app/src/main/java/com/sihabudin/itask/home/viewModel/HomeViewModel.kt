package com.sihabudin.itask.home.viewModel

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.sihabudin.itask.core.domain.usecase.TaskUseCase

class HomeViewModel(val taskUseCase: TaskUseCase) : ViewModel() {
    val getCategory = LiveDataReactiveStreams.fromPublisher(taskUseCase.getAllCategory())
    val getCategoryWithCountTask =
        LiveDataReactiveStreams.fromPublisher(taskUseCase.getCategoryWithCountTask())
}