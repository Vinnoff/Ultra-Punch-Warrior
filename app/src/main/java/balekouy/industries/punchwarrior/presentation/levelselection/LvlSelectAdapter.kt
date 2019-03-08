package balekouy.industries.punchwarrior.presentation.levelselection

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.data.models.Level
import balekouy.industries.punchwarrior.presentation.UPWUtils.Companion.getDrawableFromIdentifier
import kotlinx.android.synthetic.main.item_oponent.view.*

class LvlSelectAdapter(val context: Context, data: List<Level>, var listener: Listener) :
    RecyclerView.Adapter<LvlSelectAdapter.ViewHolder>() {

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = data[position]
        holder.bindItem(model, position)
        ViewCompat.setTransitionName(holder.itemView.lvl_image, "id opponent ${model.fighter.id}")
        holder.itemView.setOnClickListener { _ ->
            if (model.isUnlocked) {
                ViewCompat.getTransitionName(holder.itemView.lvl_image)?.let {
                    listener.onLevelClick(position, model, it, holder.itemView.lvl_image)
                } ?: also {
                    listener.onLevelError(position)
                }
            } else {
                listener.onLevelLocked(position)
            }
        }

    }

    class ViewHolder(val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var itemPosition: Int = -1

        fun bindItem(model: Level, position: Int) {
            itemPosition = position
            with(itemView) {
                if (model.isUnlocked) {
                    lvl_fighter_name.text = model.fighter.name
                    lvl_image.setImageDrawable(
                        getDrawableFromIdentifier(
                            context,
                            model.fighter.sprites.portrait
                        )
                    )
                } else {
                    lvl_fighter_name.visibility = View.GONE
                    lvl_image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_lock))
                }
            }
        }
    }

    interface Listener {
        fun onLevelClick(position: Int, level: Level, transitionName: String, transitionView: View)
        fun onLevelError(position: Int)
        fun onLevelLocked(position: Int)
    }
}