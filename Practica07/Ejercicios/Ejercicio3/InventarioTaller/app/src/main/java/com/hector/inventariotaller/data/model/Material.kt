package com.hector.inventariotaller.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "materiales")
data class Material(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val categoria: String,
    val stock: Int,
    val precioUnitario: Double,
    val proveedor: String
)