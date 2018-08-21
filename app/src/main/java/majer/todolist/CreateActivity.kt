package majer.todolist

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_create.*
import majer.todolist.db.TodoDbTable
import java.util.*

class CreateActivity : AppCompatActivity() {

    private val TAG = CreateActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

    }

    fun save(v: View) {
        if (et_title.isBlank() || et_descr.isBlank() || tv_data.isBlank()) {
            Log.d(TAG, "title, description or date missing")
            displayErrorMessage("Brak tytułu, opisu lub daty")
            return
        }

        val title = et_title.text.toString()
        val description = et_descr.text.toString()
        val date = tv_data.text.toString()
        val tdList = TDList(title, description, date)

        val id = TodoDbTable(this).store(tdList)

        if (id == -1L) {
            displayErrorMessage("Element could not be stored")
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    fun funDate(v: View) {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val dpd = DatePickerDialog(this, android.R.style.Theme_Holo_Dialog,
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth
                    -> tv_data.text = "$dayOfMonth / ${monthOfYear +1} / $year" },
                year, month, day)

        dpd.show()
    }

    private fun displayErrorMessage(message: String) {
        tv_error.text = message
        tv_error.visibility = View.VISIBLE
    }
}

private fun EditText.isBlank() = this.text.toString().isBlank()
private fun TextView.isBlank() = this.text.toString().isBlank()
