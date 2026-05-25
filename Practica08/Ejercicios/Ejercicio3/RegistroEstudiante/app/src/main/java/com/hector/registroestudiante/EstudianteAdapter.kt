package com.hector.registroestudiante

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EstudianteAdapter(
    private val lista: List<Estudiante>,
    private val onEditar: (Estudiante) -> Unit,
    private val onEliminar: (Estudiante) -> Unit
) : RecyclerView.Adapter<EstudianteAdapter.EstudianteViewHolder>() {

    class EstudianteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtItemNombre)
        val txtCarrera: TextView = view.findViewById(R.id.txtItemCarrera)
        val txtCurso: TextView = view.findViewById(R.id.txtItemCurso)
        val btnEditar: ImageButton = view.findViewById(R.id.btnEditar)
        val btnEliminar: ImageButton = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EstudianteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_estudiante, parent, false)
        return EstudianteViewHolder(view)
    }

    override fun onBindViewHolder(holder: EstudianteViewHolder, position: Int) {
        val estudiante = lista[position]
        holder.txtNombre.text = estudiante.nombre
        holder.txtCarrera.text = "Carrera: ${estudiante.carrera}"
        holder.txtCurso.text = "Curso: ${estudiante.curso}"

        holder.btnEditar.setOnClickListener { onEditar(estudiante) }
        holder.btnEliminar.setOnClickListener { onEliminar(estudiante) }
    }

    override fun getItemCount(): Int = lista.size
}