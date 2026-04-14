package com.hector.galeria

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Producto(
    val nombre: String,
    val cantidad: Int,
    val precio: Double,
    val imagenResId: Int
)

class ProductoAdapter(
    private val productos: List<Producto>,
    private val onProductoClick: (Int) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewItem)
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombre)
        val txtCantidad: TextView = itemView.findViewById(R.id.txtCantidad)
        val txtPrecio: TextView = itemView.findViewById(R.id.txtPrecio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = productos[position]
        holder.imageView.setImageResource(producto.imagenResId)
        holder.txtNombre.text = producto.nombre
        holder.txtCantidad.text = "Cantidad: ${producto.cantidad}"
        holder.txtPrecio.text = "Precio: $${producto.precio}"
        holder.itemView.setOnClickListener { onProductoClick(position) }
    }

    override fun getItemCount(): Int = productos.size
}