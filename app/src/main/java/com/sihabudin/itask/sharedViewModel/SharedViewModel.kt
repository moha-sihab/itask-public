package com.sihabudin.itask.sharedViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sihabudin.itask.core.domain.model.CategoryModel
import com.sihabudin.itask.core.domain.model.SubTaskModel

class SharedViewModel : ViewModel() {

    private var _categorySelected = MutableLiveData<CategoryModel>()
    private var _subTask = MutableLiveData<SubTaskModel>()

    val categorySelected: LiveData<CategoryModel> = _categorySelected

    fun setCategorySelected(selectedCategory: CategoryModel) {
        _categorySelected.value = selectedCategory
    }

    val subTask: LiveData<SubTaskModel> = _subTask

    fun setSubTask(subTask: SubTaskModel) {
        _subTask.value = subTask
    }


}