package fi.johannes

/**
 * Johannes on 21.3.2022.
 */

val getEnv = { key: String, default: String -> System.getenv(key) ?: default }
fun main(args: Array<String>) {
    val bootstrap = getEnv("KAFKA_BOOTSTRAP", "kafka-bootstrap:9092");
    val schemaRegistry = getEnv("SCHEMA_REGISTRY", "schema-registry:8081");
    SimpleProducer(bootstrap, schemaRegistry).produce(5)
}