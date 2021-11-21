package com.sihabudin.itask.addCategory.viewModel

import androidx.lifecycle.ViewModel
import com.sihabudin.itask.core.domain.model.CategoryModel
import com.sihabudin.itask.core.domain.usecase.TaskUseCase

class AddCategoryViewModel(val taskUseCase: TaskUseCase) : ViewModel() {
    fun insertCategory(categoryModel: CategoryModel) = taskUseCase.insertCategory(categoryModel)

}