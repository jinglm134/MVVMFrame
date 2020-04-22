package com.project.mvvmframe.util

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.project.mvvmframe.app.BaseApp
import java.io.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @CreateDate 2020/4/22 9:48
 * @Author jaylm
 */
class SPUtils<T>(private val key: String, private val default: T) : ReadWriteProperty<Any?, T> {
    companion object {
        private val prefs: SharedPreferences by lazy {
            PreferenceManager.getDefaultSharedPreferences(BaseApp.context)
        }

        /**
         * 序列化对象
         * @param obj
         * @return
         * @throws IOException
         */
        @Throws(IOException::class)
        private fun <A> serialize(obj: A): String {
            val byteArrayOutputStream = ByteArrayOutputStream()
            val objectOutputStream = ObjectOutputStream(
                byteArrayOutputStream
            )
            objectOutputStream.writeObject(obj)
            var serStr = byteArrayOutputStream.toString("ISO-8859-1")
            serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
            objectOutputStream.close()
            byteArrayOutputStream.close()
            return serStr
        }

        /**
         * 反序列化对象
         * @param str
         * @return
         * @throws IOException
         * @throws ClassNotFoundException
         */
        @Suppress("UNCHECKED_CAST")
        @Throws(IOException::class, ClassNotFoundException::class)
        private fun <A> unSerialize(str: String): A {
            val redStr = java.net.URLDecoder.decode(str, "UTF-8")
            val byteArrayInputStream = ByteArrayInputStream(
                redStr.toByteArray(charset("ISO-8859-1"))
            )
            val objectInputStream = ObjectInputStream(
                byteArrayInputStream
            )
            val obj = objectInputStream.readObject() as A
            objectInputStream.close()
            byteArrayInputStream.close()
            return obj
        }


        /**
         * 查询某个key是否已经存在
         */
        fun contains(key: String): Boolean {
            return prefs.contains(key)
        }

        /**
         * 返回所有的键值对
         */
        fun getAll(): Map<String, *> {
            return prefs.all
        }

        /**
         * 删除全部数据
         */
        fun clearPreference() {
            prefs.edit().clear().apply()
        }

        /**
         * 根据key删除存储数据
         */
        fun clearPreference(key: String) {
            prefs.edit().remove(key).apply()
        }
    }


    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getValue(key, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putValue(key, value)
    }

    private fun <T> putValue(key: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            else -> putString(key, serialize(value))
        }.apply()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getValue(key: String, default: T): T {
        return with(prefs) {
            when (default) {
                is String -> getString(key, default) as T
                is Boolean -> getBoolean(key, default) as T
                is Int -> getInt(key, default) as T
                is Long -> getLong(key, default) as T
                is Float -> getFloat(key, default) as T
                else -> unSerialize(getString(key, serialize(default))!!) as T
            }
        }
    }
}