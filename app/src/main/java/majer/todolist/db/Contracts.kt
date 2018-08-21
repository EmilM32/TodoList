package majer.todolist.db

import android.provider.BaseColumns

val DATABASE_NAME = "todolist.db"
val DATABASE_VERSION = 10

object ListEntry : BaseColumns {
    val TABLE_NAME = "todo"
    val _ID = "id"
    val TITLE_COL = "title"
    val DESCR_COL = "description"
    val DATE_COL = "date"
}