package majer.todolist.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import majer.todolist.TDList
import majer.todolist.db.ListEntry.DATE_COL
import majer.todolist.db.ListEntry.DESCR_COL
import majer.todolist.db.ListEntry.TABLE_NAME
import majer.todolist.db.ListEntry.TITLE_COL
import majer.todolist.db.ListEntry._ID

class TodoDbTable(context: Context) {

    private val TAG = TodoDbTable::class.java.simpleName

    private val dbHelper = TodolistDB(context)

    fun store(tdlist: TDList): Long {
        val db = dbHelper.writableDatabase

        val values = ContentValues()
        with(values) {
            put(TITLE_COL, tdlist.title)
            put(DESCR_COL, tdlist.description)
            put(DATE_COL, tdlist.date)
        }

        val id = db.transaction {
            insert(TABLE_NAME, null, values)
        }

        Log.d(TAG, "Stored new element to the DB $tdlist")

        return id
    }

    fun readAllElements(): List<TDList> {

        val columns = arrayOf(_ID, TITLE_COL, DESCR_COL, DATE_COL)
        val db = dbHelper.readableDatabase

        val order = "$_ID ASC"

        val cursor = db.doQuery(TABLE_NAME, columns, orderBy = order)

        return parseElementsFrom(cursor)
    }

    private fun parseElementsFrom(cursor: Cursor): MutableList<TDList> { //CTRL+ALT+M
        val lists = mutableListOf<TDList>()
        while (cursor.moveToNext()) {
            val title = cursor.getString(TITLE_COL)
            val description = cursor.getString(DESCR_COL)
            val date = cursor.getString(DATE_COL)
            lists.add(TDList(title, description, date))
        }
        cursor.close()
        return lists
    }
}

private fun Cursor.getString(columnName: String) = getString(getColumnIndex(columnName))

private fun SQLiteDatabase.doQuery(table: String, columns: Array<String>, selection: String? = null,
                                   selectionArgs: Array<String>? = null, groupBy: String? = null,
                                   having: String? = null, orderBy: String? = null): Cursor {

    return query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
}

private inline fun <T> SQLiteDatabase.transaction(function: SQLiteDatabase.() -> T): T {
    beginTransaction()
    val result = try {
        val returnValue = function()
        setTransactionSuccessful()

        returnValue
    } finally {
        endTransaction()
    }
    close()

    return result
}