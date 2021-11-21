package com.sihabudin.itask.category.viewModel

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.sihabudin.itask.core.domain.model.CategoryModel
import com.sihabudin.itask.core.domain.usecase.TaskUseCase

class CategoryViewModel (val taskUseCase: TaskUseCase) : ViewModel() {

    val getCategory = LiveDataReactiveStreams.fromPublisher(taskUseCase.getActiveCategory())
    val getAllCategory = LiveDataReactiveStreams.fromPublisher(taskUseCase.getAllCategory())
    fun updateCategory(categoryModel: CategoryModel)
    {
        taskUseCase.updateCategory(categoryModel)
    }
}