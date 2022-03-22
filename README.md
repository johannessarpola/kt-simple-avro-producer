# kt-simple-avro-producer

Use env variables to connect it to broker and schema registry

- name: SCHEMA_REGISTRY
  value: <addr:port>
- name: KAFKA_BOOTSTRAP
  value: <addr:port>


default schema, expects it to exist in schema registry
```
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
```
