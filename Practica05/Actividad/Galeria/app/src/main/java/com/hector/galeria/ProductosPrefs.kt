package com.hector.galeria

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductosPrefs(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("productos_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun guardarProductos(lista: List<Producto>) {
        val json = gson.toJson(lista)
        prefs.edit().putString("productos", json).apply()
    }

    fun cargarProductos(): List<Producto> {
        val json = prefs.getString("productos", null) ?: return emptyList()
        val type = object : TypeToken<List<Producto>>() {}.type
        return gson.fromJson(json, type)
    }
}