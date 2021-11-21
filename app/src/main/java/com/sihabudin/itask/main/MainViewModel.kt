package com.sihabudin.itask.main

import androidx.lifecycle.ViewModel
import com.sihabudin.itask.core.domain.model.CategoryModel
import com.sihabudin.itask.core.domain.usecase.TaskUseCase

class MainViewModel(val taskUseCase: TaskUseCase) : ViewModel() {
    fun insertCategory(categoryModel: CategoryModel) = taskUseCase.insertCategory(categoryModel)
}