package dam.a48965.project_48965.domain

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {

    private val moshi = Moshi.Builder().build()

    @TypeConverter
    fun fromString(value: String): List<Int>? {
        val listType = Types.newParameterizedType(List::class.java, Integer::class.java)
        val adapter = moshi.adapter<List<Int>>(listType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromList(list: List<Int>?): String {
        val listType = Types.newParameterizedType(List::class.java, Integer::class.java)
        val adapter = moshi.adapter<List<Int>>(listType)
        return adapter.toJson(list)
    }

    @TypeConverter
    fun fromStringToStringList(value: String): List<String>? {
        val listType = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(listType)
        return adapter.fromJson(value)
    }

    @TypeConverter
    fun fromStringListToString(list: List<String>?): String {
        val listType = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(listType)
        return adapter.toJson(list)
    }
}
