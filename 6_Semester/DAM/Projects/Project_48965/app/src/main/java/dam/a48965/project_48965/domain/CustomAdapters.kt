package dam.a48965.project_48965.domain

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

class StringListAdapter {
    @ToJson
    fun toJson(writer: JsonWriter, value: List<String>?) {
        value?.let {
            writer.beginArray()
            for (s in it) {
                writer.value(s)
            }
            writer.endArray()
        }
    }

    @FromJson
    fun fromJson(reader: JsonReader): List<String> {
        val result = mutableListOf<String>()
        reader.beginArray()
        while (reader.hasNext()) {
            result.add(reader.nextString())
        }
        reader.endArray()
        return result
    }
}


class IntListAdapter {
    @ToJson
    fun toJson(writer: JsonWriter, value: List<Int>?) {
        value?.let {
            writer.beginArray()
            for (i in it) {
                writer.value(i)
            }
            writer.endArray()
        }
    }

    @FromJson
    fun fromJson(reader: JsonReader): List<Int> {
        val result = mutableListOf<Int>()
        reader.beginArray()
        while (reader.hasNext()) {
            result.add(reader.nextInt())
        }
        reader.endArray()
        return result
    }
}
