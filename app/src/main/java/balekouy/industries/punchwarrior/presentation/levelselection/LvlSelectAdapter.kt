package balekouy.industries.punchwarrior.presentation.levelselection

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.data.models.Level
import kotlinx.android.synthetic.main.item_oponent.view.*

class LvlSelectAdapter(val context: Context, data: List<Level>) : RecyclerView.Adapter<LvlSelectAdapter.ViewHolder>() {

    var data: List<Level> = data
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_oponent, parent, false)
        return ViewHolder(context, itemView)
    }


    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindItem(data[position], position)

    class ViewHolder(val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(model: Level, position: Int) {
            with(itemView) {
                lvl_fighter_name.text = model.fighter.second.name
                lvl_image.setImageDrawable(ContextCompat.getDrawable(context, model.fighter.second.portraitRes))
            }
        }
    }
}