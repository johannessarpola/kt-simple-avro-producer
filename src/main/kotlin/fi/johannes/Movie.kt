package fi.johannes

import java.time.LocalDate

/**
 * Johannes on 1.4.2022.
 */
class Movie(val title: String = "", val year: Int = 0, val genre: String = Genre.unknown.toString()) :
    Randomized<Movie>, Schema {
    override fun randomize(): Movie {
        return Movie(
            title = faker.book().title(),
            year = faker.number().numberBetween(LocalDate.now().minusYears(20).year, LocalDate.now().year),
            genre = Genre.values().get(faker.number().numberBetween(0, Genre.values().size)).toString()
        )
    }

}
