package com.gorbunov.nettestclassic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gorbunov.nettestclassic.NetTestRecyclerAdapter.ViewHolder
import com.gorbunov.nettestclassic.model.NetTestItem


/**
 * Адаптер для recycler view
 */
class NetTestRecyclerAdapter(private val list: List<NetTestItem>) :
    RecyclerView.Adapter<ViewHolder>() {

    /**
     * Функция создания вьюшки с отдельным элементом списка
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(itemView)
    }

    /**
     * Функция наполнения данными каждого элемента
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categoryText.text = list[position].category
        val countText = "${list[position].count} курсов"
        holder.countText.text = countText
    }

    /**
     * Функция для получения количества элементов
     */
    override fun getItemCount(): Int = list.size


    /**
     * Класс для отдельного элемента списка
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryText = itemView.findViewById<TextView>(R.id.category)
        val countText = itemView.findViewById<TextView>(R.id.count)
    }

}