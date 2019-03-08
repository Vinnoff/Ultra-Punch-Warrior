package balekouy.industries.punchwarrior.presentation.scores

import android.content.Context
import android.support.v4.content.ContextCompat.getColor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.data.models.Score
import balekouy.industries.punchwarrior.presentation.UPWUtils.Companion.getDrawableFromIdentifier
import kotlinx.android.synthetic.main.item_score.view.*

class ScoresAdapter(val context: Context, listModel: List<Score>) : RecyclerView.Adapter<ScoresAdapter.ViewHolder>() {

    var data: List<Score> = listModel
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_score, parent, false)
        return ViewHolder(context, itemView)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindItem(data[position], position)

    class ViewHolder(val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(model: Score, position: Int) {
            with(itemView) {
                when (position) {
                    Position.FIRST.ordinal -> score_root.setBackgroundColor(getColor(context, R.color.first_score))
                    Position.SECOND.ordinal -> score_root.setBackgroundColor(
                        getColor(
                            context,
                            R.color.second_score
                        )
                    )
                    Position.THIRD.ordinal -> score_root.setBackgroundColor(getColor(context, R.color.third_score))
                    else -> score_root.setBackgroundColor(getColor(context, R.color.transparent_high_white))
                }
                score_name.text = model.name
                score_value.text = model.score.toString()
                model.fighter.let {
                    score_fighter.setImageDrawable(
                        getDrawableFromIdentifier(
                            context,
                            it.sprites.portrait
                        )
                    )
                }
                score_level.text = model.difficulty?.string ?: "NONE"
            }
        }
    }

    enum class Position {
        FIRST, SECOND, THIRD
    }
}
