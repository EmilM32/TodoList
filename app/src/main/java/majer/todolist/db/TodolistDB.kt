package majer.todolist.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TodolistDB(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val SQL_CREATE_ENTRIES = "CREATE TABLE ${ListEntry.TABLE_NAME} (" +
            "${ListEntry._ID} INTEGER PRIMARY KEY," +
            "${ListEntry.TITLE_COL} TEXT," +
            "${ListEntry.DESCR_COL} TEXT," +
            "${ListEntry.DATE_COL} TEXT" +
            ")"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${ListEntry.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun deleteData(id : String): Int {
        val db = this.writableDatabase
        return db.delete(ListEntry.TABLE_NAME,"title = ?", arrayOf(id))
    }

}