package fi.johannes

import com.google.gson.Gson
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.avro.Schema
import org.apache.avro.generic.GenericData
import org.apache.avro.generic.GenericRecord
import org.apache.avro.generic.GenericRecordBuilder
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.log4j.LogManager
import java.util.*

class SimpleProducer(brokers: String, schemaRegistryUrl: String, val topic: String) {

    private val logger = LogManager.getLogger(javaClass)
    private val producer = createProducer(brokers, schemaRegistryUrl)

    private fun createProducer(brokers: String, schemaRegistryUrl: String): Producer<String, GenericRecord> {
        val props = Properties()
        props["bootstrap.servers"] = brokers
        props["key.serializer"] = StringSerializer::class.java
        props["value.serializer"] = KafkaAvroSerializer::class.java
        props["schema.registry.url"] = schemaRegistryUrl
        return KafkaProducer<String, GenericRecord>(props)
    }

    fun produce(waitTime: Int, source: () -> fi.johannes.Schema) {
        val waitTimeBetweenIterationsMs = waitTime * 1000L
        logger.info("Producing record every ${waitTimeBetweenIterationsMs}ms ")

        while (true) {

            val nxt = source()
            logger.info("Producing $nxt")
            val futureResult = producer.send(ProducerRecord(topic, nxt.toRecord()))
            Thread.sleep(waitTimeBetweenIterationsMs)
            futureResult.get()
        }
    }
}
