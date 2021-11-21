package com.sihabudin.itask.core.utils

import com.sihabudin.itask.core.data.source.local.entity.*
import com.sihabudin.itask.core.domain.model.*

object DataMapper {

    fun mapTaskEntitiesToDomain(input: List<TaskEntity>): List<TaskModel> =
    input.map{
        TaskModel(
            id_task = it.id_task,
            id_category = it.id_category,
            title = it.title,
            detail = it.detail,
            due_date =  it.due_date,
            isSetDate = it.isSetDate,
            isAlarm =  it.isAlarm,
            idAlarm = it.idAlarm,
            status = it.status,
            createBy = it.createBy,
            createDate = it.createDate,
            updateBy = it.updateBy,
            updateDate = it.updateDate
        )
    }

    fun mapCategoryEntitiesToDomain(input: List<CategoryEntity>): List<CategoryModel> =
    input.map {
        CategoryModel(
            id_category = it.id_category,
            category = it.category,
            isActive = it.isActive,
            colorLabel = it.colorLabel,
            createBy = it.createBy,
            createDate = it.createDate,
            updateBy = it.updateBy,
            updateDate = it.updateDate
        )
    }

    fun mapCategoryWithCountTaskToDomain(input : List<CategoryWithCountTaskEntity>) : List<CategoryWithCountTaskModel> =
        input.map{
            CategoryWithCountTaskModel(
                id_category = it.id_category,
                category = it.category,
                colorLabel = it.colorLabel,
                totalTask =  it.totalTask
            )
        }

    fun mapSubTaskEntitiesToDomain(input: List<SubTaskEntity>) : List<SubTaskModel> =
        input.map{
            SubTaskModel(
                id_subtask =  it.id_subtask,
                id_task = it.id_task,
                subtask = it.subtask,
                status = it.status
            )
        }

    fun mapTaskWithCategoryEntitiesToDomain(input: List<TaskWithCategoryEntity> ): List<TaskWithCategoryModel> =
         input.map{
             TaskWithCategoryModel(
                 id_task = it.id_task,
                 id_category = it.id_category,
                 title = it.title,
                 detail = it.detail,
                 due_date =  it.due_date,
                 isSetDate = it.isSetDate,
                 isAlarm =  it.isAlarm,
                 idAlarm = it.idAlarm,
                 status = it.status,
                 createBy = it.createBy,
                 createDate = it.createDate,
                 updateBy = it.updateBy,
                 updateDate = it.updateDate,
                 category =  it.category,
                 colorLabel = it.colorLabel
             )
         }




    fun mapTaskDomainToEntity(input: TaskModel) = TaskEntity(
        id_task = input.id_task,
        id_category = input.id_category,
        title = input.title,
        detail = input.detail,
        due_date =  input.due_date,
        isSetDate = input.isSetDate,
        isAlarm =  input.isAlarm,
        idAlarm = input.idAlarm,
        status = input.status,
        createBy = input.createBy,
        createDate = input.createDate,
        updateBy = input.updateBy,
        updateDate = input.updateDate
    )

    fun mapCategoryDomainToEntity(input: CategoryModel) = CategoryEntity(
        id_category = input.id_category,
        category = input.category,
        isActive = input.isActive,
        colorLabel = input.colorLabel,
        createBy = input.createBy,
        createDate = input.createDate,
        updateBy = input.updateBy,
        updateDate = input.updateDate
    )


    fun mapSubTaskDomainToEntity(input : SubTaskModel) = SubTaskEntity(
        id_subtask = input.id_subtask,
        id_task = input.id_task,
        subtask = input.subtask,
        status = input.status
    )


}