package com.sihabudin.itask.addTask.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sihabudin.itask.core.domain.model.SubTaskModel
import com.sihabudin.itask.core.domain.model.TaskModel
import com.sihabudin.itask.core.domain.usecase.TaskUseCase

class AddTaskViewModel(val taskUseCase: TaskUseCase) : ViewModel() {
    private var _subTask = MutableLiveData<List<SubTaskModel>>()
    private var _idAlarm = MutableLiveData<Int>()

    fun insertTask(taskModel: TaskModel) = taskUseCase.insertTask(taskModel)
    fun insertSubTask(subTaskModel: SubTaskModel) = taskUseCase.insertSubTask(subTaskModel)
    val getSubTask: LiveData<List<SubTaskModel>> = _subTask
    fun setSubTask(subTask: MutableList<SubTaskModel>) {
        _subTask.postValue(subTask)
    }
    val getIdAlarm : LiveData<Int> = _idAlarm
    fun setIdAlarm(idAlarm : Int){
        _idAlarm.postValue(idAlarm)
    }
    fun getNextIdAlarm() = taskUseCase.getNextIdAlarm()
}