package dam.a48965.project_48965.domain

import com.google.gson.*
import java.lang.reflect.Type

class IntListDeserializer : JsonDeserializer<List<Int>> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): List<Int> {
        val jsonArray = json.asJsonArray
        val intList = mutableListOf<Int>()
        jsonArray.forEach { intList.add(it.asInt) }
        return intList
    }
}

class StringListDeserializer : JsonDeserializer<List<String>> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): List<String> {
        val jsonArray = json.asJsonArray
        val stringList = mutableListOf<String>()
        jsonArray.forEach { stringList.add(it.asString) }
        return stringList
    }
}