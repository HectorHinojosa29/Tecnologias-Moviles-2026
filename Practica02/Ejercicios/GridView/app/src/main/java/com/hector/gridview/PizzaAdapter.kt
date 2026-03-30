package com.hector.gridview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
class PizzaAdapter(
    private val context: Context,
    private val listaPizzas: List<Pizza>
) : BaseAdapter() {
    override fun getCount(): Int = listaPizzas.size
    override fun getItem(position: Int): Any = listaPizzas[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val vista: View
        val holder: ViewHolder

        if (convertView == null) {
            vista = LayoutInflater.from(context).inflate(R.layout.item_grid, parent, false)
            holder = ViewHolder()
            holder.imagen = vista.findViewById(R.id.imgPizza)
            vista.tag = holder
        } else {
            vista = convertView
            holder = vista.tag as ViewHolder
        }

        val pizza = listaPizzas[position]
        holder.imagen?.setImageResource(pizza.imagen)

        return vista
    }
    private class ViewHolder {
        var imagen: ImageView? = null
    }
}