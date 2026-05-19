package com.hector.inventariotaller.data.dao

import androidx.room.*
import com.hector.inventariotaller.data.model.Material
import kotlinx.coroutines.flow.Flow

@Dao
interface MaterialDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(material: Material): Long

    @Update
    suspend fun actualizar(material: Material)

    @Delete
    suspend fun eliminar(material: Material)

    @Query("SELECT * FROM materiales ORDER BY nombre ASC")
    fun listarTodos(): Flow<List<Material>>

    @Query("SELECT * FROM materiales WHERE id = :id")
    suspend fun obtenerPorId(id: Int): Material?

    @Query("SELECT * FROM materiales WHERE stock < :minimo")
    suspend fun listarStockBajo(minimo: Int): List<Material>
}