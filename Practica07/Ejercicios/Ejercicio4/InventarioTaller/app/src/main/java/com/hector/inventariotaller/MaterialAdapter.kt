package com.hector.inventariotaller

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hector.inventariotaller.data.model.Material

class MaterialAdapter(
    private var lista: List<Material>,
    private val onEliminar: (Material) -> Unit
) : RecyclerView.Adapter<MaterialAdapter.VH>() {

    inner class VH(val view: android.view.View) : RecyclerView.ViewHolder(view) {
        val tv: TextView = view.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val m = lista[position]
        holder.tv.text = "📦 ${m.nombre} (${m.categoria}) — Stock: ${m.stock} — S/. ${m.precioUnitario} — ${m.proveedor}"
        holder.view.setOnLongClickListener {
            onEliminar(m)
            true
        }
    }

    override fun getItemCount() = lista.size

    fun actualizar(nueva: List<Material>) {
        lista = nueva
        notifyDataSetChanged()
    }
}