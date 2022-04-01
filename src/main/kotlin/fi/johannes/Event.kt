package fi.johannes

import java.util.*

data class Event(
    val id: String = "",
    val source: String = "",
    val severity: Int = 0,
    val category: Int = 0,
    val description: String = ""
) : Randomized<Event>, Schema {

    override fun randomize(): Event {
        return Event(
            id = UUID.randomUUID().toString(),
            source = faker.company().name(),
            severity = faker.number().numberBetween(1, 4).toInt(),
            category = faker.number().numberBetween(1, 7).toInt(),
            description = faker.yoda().quote().toString()
        )
    }
}