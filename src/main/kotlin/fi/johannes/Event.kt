package fi.johannes

data class Event(
    val id: String,
    val source: String,
    val severity: Int,
    val category: Int,
    val description: String
)