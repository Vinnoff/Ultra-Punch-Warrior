package balekouy.industries.punchwarrior.presentation.scores

import android.content.Context
import android.support.v4.content.ContextCompat.getColor
import android.support.v4.content.ContextCompat.getDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.data.models.Score
import kotlinx.android.synthetic.main.item_score.view.*

class ScoresAdapter(val context: Context, var listModel: List<Score>) :
    RecyclerView.Adapter<ScoresAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_score, parent, false)
        return ViewHolder(context, itemView)
    }

    override fun getItemCount() = listModel.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindItem(listModel[position], position)

    class ViewHolder(val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(model: Score, position: Int) {
            when (position) {
                Position.FIRST.ordinal -> itemView.score_root.setBackgroundColor(getColor(context, R.color.first_score))
                Position.SECOND.ordinal -> itemView.score_root.setBackgroundColor(
                    getColor(
                        context,
                        R.color.second_score
                    )
                )
                Position.THIRD.ordinal -> itemView.score_root.setBackgroundColor(getColor(context, R.color.third_score))
            }
            with(itemView) {
                score_name.text = model.name
                score_value.text = model.score.toString()
                model.fighter?.let { score_fighter.setImageDrawable(getDrawable(context, it.imageSet)) }
                score_level.text = model.difficulty.string
            }
        }
    }

    enum class Position {
        FIRST, SECOND, THIRD
    }
}
