package fr.epita.tictactoe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.score_list_row.view.*

class PlayerScoreAdapter(val data : List<PlayerScore>) : BaseAdapter() {
    // viewHolder is used to avoid looking for TextViews in the layout for each MovieCharacter in the list
    // This is necessary for optimization
    class ViewHolder(itemView : View) {
        val nameTextView : TextView = itemView.player_name
        val resultImg : ImageView = itemView.result
        val dateTextView : TextView =  itemView.date
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView : View
        val viewHolder : ViewHolder
        // convertView is not null if an instance of rowView was previously created and outside of the screen (Recycling)
        if (convertView == null) {
            rowView = LayoutInflater.from(parent!!.context).inflate(R.layout.score_list_row, parent, false)
            viewHolder = ViewHolder(rowView)
            rowView.tag = viewHolder
        } else {
            // There already is an instance of rowView instanciated that we can use
            // We don't need to get TextViews from the layout thanks to the ViewHolder, which is kept thanks to the tag
            rowView = convertView
            viewHolder = rowView.tag as ViewHolder
        }

        viewHolder.nameTextView.text = getItem(position).player
        // viewHolder.resultImg.imageAlpha = getItem(position).lastName
        viewHolder.dateTextView.text = getItem(position).date.toString()

        return rowView
    }

    override fun getItem(position: Int): PlayerScore {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        // If no ID, just send the position back
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }
}