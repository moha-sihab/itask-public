package com.sihabudin.itask.di

import com.sihabudin.itask.addCategory.viewModel.AddCategoryViewModel
import com.sihabudin.itask.addTask.viewModel.AddTaskViewModel
import com.sihabudin.itask.category.viewModel.CategoryViewModel
import com.sihabudin.itask.core.domain.usecase.TaskInteractor
import com.sihabudin.itask.core.domain.usecase.TaskUseCase
import com.sihabudin.itask.core.utils.AlarmWorker
import com.sihabudin.itask.detailTask.viewModel.DetailTaskViewModel
import com.sihabudin.itask.editCategory.viewModel.EditCategoryViewModel
import com.sihabudin.itask.history.viewModel.HistoryViewModel
import com.sihabudin.itask.home.viewModel.HomeViewModel
import com.sihabudin.itask.main.MainViewModel
import com.sihabudin.itask.search.viewModel.SearchViewModel
import com.sihabudin.itask.task.viewModel.TaskNextWeekViewModel
import com.sihabudin.itask.task.viewModel.TaskTodayViewModel
import com.sihabudin.itask.task.viewModel.TaskTomorrowViewModel
import com.sihabudin.itask.taskByCategory.viewModel.TaskByCategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val useCaseModule = module {
    factory<TaskUseCase> { TaskInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { CategoryViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { TaskTodayViewModel(get()) }
    viewModel { TaskTomorrowViewModel(get()) }
    viewModel { TaskNextWeekViewModel(get()) }
    viewModel { DetailTaskViewModel(get()) }
    viewModel { TaskByCategoryViewModel(get()) }
    viewModel { AddTaskViewModel(get()) }
    viewModel { AddCategoryViewModel(get()) }
    viewModel { EditCategoryViewModel(get()) }
    viewModel { SearchViewModel(get()) }

}

val workModule = module {
    factory<TaskUseCase> { TaskInteractor(get()) }
    worker { AlarmWorker(get(), get(), get())}
}

