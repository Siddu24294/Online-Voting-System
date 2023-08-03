package com.example.onlinevotingsystem

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val mList: List<Candidate>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private lateinit var mListener:onItemCLickListener

    interface onItemCLickListener{
        fun onItemClick(position:Int){

        }
    }

    fun setOnItemClickListener(listener: onItemCLickListener){
        mListener=listener
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.candidate_card, parent, false)

        return ViewHolder(view,mListener)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.tid.text = ItemsViewModel.candId.toString()
        holder.tname.text=ItemsViewModel.candname

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View,listener: onItemCLickListener) : RecyclerView.ViewHolder(ItemView) {
        val tid: TextView = itemView.findViewById(R.id.tid)
        val tname: TextView = itemView.findViewById(R.id.tname)
        val card_layout: LinearLayout=itemView.findViewById(R.id.card_layout)
        init{
            itemView.setOnClickListener{
                listener.onItemClick(bindingAdapterPosition)

            }
        }
    }
}