package majer.todolist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_create.view.*
import kotlinx.android.synthetic.main.single_card.view.*

class Adapter(val elements: List<TDList>) : RecyclerView.Adapter<Adapter.ElementsViewHolder>() {

    class ElementsViewHolder(val card: View) : RecyclerView.ViewHolder(card)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ElementsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_card, parent, false)
        return ElementsViewHolder(view)
    }

    override fun getItemCount(): Int = elements.size

    override fun onBindViewHolder(holder: Adapter.ElementsViewHolder, index: Int) {
        val element = elements[index]
        holder.card.tv_title.text = element.title
        holder.card.tv_description.text = element.description
        holder.card.tv_date.text = element.date
    }

}