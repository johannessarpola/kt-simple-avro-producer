package fi.johannes

import com.google.gson.Gson
import org.apache.avro.Schema
import org.apache.avro.generic.GenericData

/**
 * Johannes on 1.4.2022.
 */
interface Schema {
    fun loadSchema(): String? {
        val cls = this.javaClass.simpleName.lowercase()
        val schema = this::class.java.classLoader.getResource("$cls.avsc")?.readText()
        return schema;
    }

    private fun parse(schema: String?): Schema? {
        return Schema.Parser().parse(schema)
    }

    fun toRecord(): GenericData.Record {

        val record = GenericData.Record(parse(this.loadSchema()))
        val gson = Gson()
        val json = gson.toJsonTree(this)
        json.asJsonObject.entrySet().forEach loop@{ it ->
            val v = it.value

            try {
                val tmp = v.asInt
                record.put(it.key, tmp)
                return@loop
            } catch (ex: Exception) {
                // ignore
            }

            if (v.asString.equals("false", ignoreCase = false)
                || v.asString.equals("true", ignoreCase = false)
            ) {
                try {
                    val tmp = v.asBoolean
                    record.put(it.key, tmp)
                    return@loop
                } catch (ex: Exception) {
                    // ignore
                }
            }

            try {
                val i = v.asString
                record.put(it.key, i)
                return@loop
            } catch (ex: Exception) {
                // ignore
            }


        }
        return record;
    }
}