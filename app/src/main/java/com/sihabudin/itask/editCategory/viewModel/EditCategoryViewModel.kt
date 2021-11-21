package com.sihabudin.itask.editCategory.viewModel

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.sihabudin.itask.core.domain.model.CategoryModel
import com.sihabudin.itask.core.domain.usecase.TaskUseCase

class EditCategoryViewModel(val taskUseCase: TaskUseCase) : ViewModel() {
    fun getCategory(idCategory : Int)= LiveDataReactiveStreams.fromPublisher(taskUseCase.getCategoryById(idCategory))

    fun updateCategory(categoryModel: CategoryModel)
    {
        taskUseCase.updateCategory(categoryModel)
    }
}