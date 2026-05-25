package com.hector.registroestudiante

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EstudianteAdapter(
    private val lista: List<Estudiante>
) : RecyclerView.Adapter<EstudianteAdapter.EstudianteViewHolder>() {

    class EstudianteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtItemNombre)
        val txtCarrera: TextView = view.findViewById(R.id.txtItemCarrera)
        val txtCurso: TextView = view.findViewById(R.id.txtItemCurso)
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
    }

    override fun getItemCount(): Int = lista.size
}