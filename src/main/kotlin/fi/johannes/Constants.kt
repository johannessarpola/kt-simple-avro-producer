package fi.johannes

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

val eventTopic = "schemaevents"

val jsonMapper = ObjectMapper().apply {
    registerKotlinModule()
    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    setDateFormat(StdDateFormat())
}
