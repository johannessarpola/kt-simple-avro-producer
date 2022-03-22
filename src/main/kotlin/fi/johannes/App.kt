package fi.johannes

/**
 * Johannes on 21.3.2022.
 */

val getProperty = { key: String, default: String -> System.getProperty(key) ?: default }
fun main(args: Array<String>) {
    val bootstrap = getProperty("KAFKA_BOOTSTRAP", "kafka-bootstrap:9092");
    val schemaRegistry = getProperty("SCHEMA_REGISTRY", "schema-registry:8081");
    SimpleProducer(bootstrap, schemaRegistry).produce(5)
}