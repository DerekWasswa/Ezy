package com.rosen.ezy.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rosen.ezy.R
import com.rosen.ezy.domain.data.Income
import com.rosen.ezy.utils.EzyUtils
import kotlinx.android.synthetic.main.income_row.view.*

class ListAdapter(var context: Context, private var incomesList : MutableList<Income>, private var mListener : ListListener) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private val layoutInflater : LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(layoutInflater.inflate(R.layout.income_row, parent, false), context, mListener)
    }

    override fun getItemCount(): Int =  incomesList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(incomesList[position])
    }

    class ListViewHolder constructor(itemView: View, var context: Context, var mListener: ListListener) : RecyclerView.ViewHolder(itemView) {
        fun bind(item : Income) = with(itemView) {
            itemView.value.text = context.getString(R.string.income_ugx, item.value)
            itemView.timestamp.text = EzyUtils.getDateFromTimestamp(item.timestamp)
            itemView.edit.setOnClickListener { mListener.editItem(item, layoutPosition) }
            itemView.delete.setOnClickListener { mListener.deleteItem(item, layoutPosition) }
        }
    }

    interface ListListener {
        fun deleteItem(income : Income, position: Int)
        fun editItem(income : Income, position: Int)
    }
}