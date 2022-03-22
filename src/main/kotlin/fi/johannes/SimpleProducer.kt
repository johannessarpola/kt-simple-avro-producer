package fi.johannes

import com.github.javafaker.Faker
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.avro.Schema
import org.apache.avro.generic.GenericRecord
import org.apache.avro.generic.GenericRecordBuilder
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.log4j.LogManager
import java.util.*

class SimpleProducer(brokers: String, schemaRegistryUrl: String) {

    private val logger = LogManager.getLogger(javaClass)
    private val producer = createProducer(brokers, schemaRegistryUrl)
    private val schema = Schema.Parser().parse("""
        {
            "type": "record",
            "name": "event",
            "fields" : [
                {"name": "id", "type": "string"},
                {"name": "source", "type": "string"},
                {"name": "severity", "type": "int"},
                {"name": "category", "type": "int"},
                {"name": "description", "type": "string"}
          ]
        }
    """.trimIndent())

    private fun createProducer(brokers: String, schemaRegistryUrl: String): Producer<String, GenericRecord> {
        val props = Properties()
        props["bootstrap.servers"] = brokers
        props["key.serializer"] = StringSerializer::class.java
        props["value.serializer"] = KafkaAvroSerializer::class.java
        props["schema.registry.url"] = schemaRegistryUrl
        return KafkaProducer<String, GenericRecord>(props)
    }

    fun produce(waitTime: Int) {
        val waitTimeBetweenIterationsMs = waitTime * 1000L
        logger.info("Producing record every ${waitTimeBetweenIterationsMs}ms ")

        val faker = Faker()
        while (true) {
            val fe = Event(
                    id = UUID.randomUUID().toString(),
                    source = faker.company().name(),
                    severity = faker.number().numberBetween(1, 4).toInt(),
                    category = faker.number().numberBetween(1, 7).toInt(),
                    description = faker.yoda().quote().toString()
            )
            logger.info("Generated a person: $fe")

            val avroEvent = GenericRecordBuilder(schema).apply {
                set("id", fe.id)
                set("source", fe.source)
                set("severity", fe.severity)
                set("category", fe.category)
                set("description", fe.description)
            }.build()

            val futureResult = producer.send(ProducerRecord(eventTopic, avroEvent))
            logger.debug("Sent a record $fe")

            Thread.sleep(waitTimeBetweenIterationsMs)
            futureResult.get()
        }
    }
}
