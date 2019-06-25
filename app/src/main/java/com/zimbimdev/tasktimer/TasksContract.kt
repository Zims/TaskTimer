package com.zimbimdev.tasktimer


import android.provider.BaseColumns

/**
 * Created by timbuchalka for the Android Oreo using Kotlin course
 * from www.learnprogramming.academy
 */
object TasksContract {

    internal const val TABLE_NAME = "Tasks"

    // Tasks fields
    object Columns {
        const val ID = BaseColumns._ID
        const val TASK_NAME = "Name"
        const val TASK_DESCRIPTION = "Description"
        const val TASK_SORT_ORDER = "SortOrder"
    }

}