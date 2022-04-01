package fi.johannes

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

/**
 * Johannes on 1.4.2022.
 */
internal class EventTest {

    @Test
    fun testSchema() {
        val ev = Event().randomize()
        val expect = Event::class.java.classLoader.getResource("event.avsc")?.readText()
        assertEquals(ev.loadSchema(), expect)
    }

    @Test
    fun testRecord() {
        val ev = Event().randomize()

        val record = ev.toRecord();

        assertEquals(ev.id, record.get("id").toString())
        assertEquals(ev.category.toString(), record.get("category").toString())
        assertEquals(ev.description, record.get("description").toString())
        assertEquals(ev.severity.toString(), record.get("severity").toString())
        assertEquals(ev.source, record.get("source").toString())
    }
}