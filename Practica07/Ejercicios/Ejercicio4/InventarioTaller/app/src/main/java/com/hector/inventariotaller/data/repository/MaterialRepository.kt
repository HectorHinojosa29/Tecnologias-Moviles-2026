package com.hector.inventariotaller.data.repository

import com.hector.inventariotaller.data.dao.MaterialDao
import com.hector.inventariotaller.data.model.Material
import kotlinx.coroutines.flow.Flow

class MaterialRepository(private val dao: MaterialDao) {

    // Exponemos el Flow tal cual
    val materiales: Flow<List<Material>> = dao.listarTodos()

    suspend fun insertar(material: Material) = dao.insertar(material)

    suspend fun actualizar(material: Material) = dao.actualizar(material)

    suspend fun eliminar(material: Material) = dao.eliminar(material)

    suspend fun obtenerPorId(id: Int): Material? = dao.obtenerPorId(id)

    suspend fun stockBajo(minimo: Int = 10): List<Material> =
        dao.listarStockBajo(minimo)
}