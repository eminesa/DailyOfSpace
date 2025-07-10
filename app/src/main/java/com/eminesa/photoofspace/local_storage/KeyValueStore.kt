package com.eminesa.photoofspace.local_storage

interface KeyValueStore {
    fun getBool(key: String): Boolean?
    fun setBool(key: String, value: Boolean)
    fun getFloat(key: String): Float?
    fun setFloat(key: String, value: Float)
    fun getDouble(key: String): Double?
    fun setDouble(key: String, value: Double)
    fun getLong(key: String): Long?
    fun setLong(key: String, value: Long)
    fun getInt(key: String): Int?
    fun setInt(key: String, value: Int)
    fun getString(key: String): String?
    fun setString(key: String, value: String)
    fun remove(key: String)
    fun containsKey(key: String): Boolean
    fun clear(): Boolean
}
