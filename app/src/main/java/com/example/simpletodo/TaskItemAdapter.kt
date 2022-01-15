package com.example.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/*
* Bridge to tell recycle view how to display the arraylist strings
* */
class TaskItemAdapter (val listOfItems: List<String>, val longClickListener: OnLongClickListener) : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface OnLongClickListener{
        fun onItemLongClicked(position: Int)
    }

    // Usually involves inflating a layout from XML and returning the holder
    //how to layout every item in the view, how does it need to be layed out
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { //for every item
        val context = parent.context //get context
        val inflater = LayoutInflater.from(context) //use it to inflate the layout for each item
        // Inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false) //inflate it in recycle view
        // Return a new holder instance
        return ViewHolder(contactView)
    }
    //take what is in the list of items and use it to populate the layout in view holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) { //text to display
        // Get the data model based on position
        val task = listOfItems.get(position) //get item

        //text view to be text in specific task
        holder.textView.text = task //set text view to whatever the item is
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    //get references to the views needed to populate the data from the array into views
    //itemview is one row
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //references to elements in layout view
        val textView: TextView

        init{
            textView = itemView.findViewById(android.R.id.text1)

            itemView.setOnLongClickListener{
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }



}