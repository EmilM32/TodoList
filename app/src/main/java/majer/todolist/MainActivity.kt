package majer.todolist

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.single_card.view.*
import majer.todolist.db.TodoDbTable
import majer.todolist.db.TodolistDB

class MainActivity : AppCompatActivity() {

    private var helper = TodolistDB(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this)

        rv.adapter = Adapter(TodoDbTable(this).readAllElements())

        rv.addOnItemTouchListener(RecyclerTouchListener(applicationContext, rv, object : ClickListener {

            override fun onClick(view: View, position: Int) {
                dialogBuilder(view)
            }
        }))
    }


    fun dialogBuilder(v: View) {
        val builder = AlertDialog.Builder(this)

        with(builder) {
            setTitle("" + v.tv_title.text)
            setMessage(getString(R.string.dialog_desc))
            setPositiveButton("TAK") { _, _ ->
                Toast.makeText(applicationContext, "Gratulacje!", Toast.LENGTH_SHORT).show()
                helper.deleteData(v.tv_title.text as String)
                this@MainActivity.recreate()
            }
            setNegativeButton("NIE") { _, _ -> }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.btn_add) {
            switchTo(CreateActivity::class.java) //ctrl+alt+m
        }
        return true
    }

    private fun switchTo(c: Class<*>) {
        val intent = Intent(this, c)
        startActivity(intent)
    }

}
