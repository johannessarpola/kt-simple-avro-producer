# kt-simple-avro-producer

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
