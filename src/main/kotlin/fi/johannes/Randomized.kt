package fi.johannes

import com.github.javafaker.Faker

/**
 * Johannes on 1.4.2022.
 */
interface Randomized<T> {
    val faker: Faker
        get() = Faker()

    fun randomize(): T
}