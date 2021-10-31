package repository

import database.repository.CrudRepository
import domain.Genre

interface GenreRepository : CrudRepository<Genre, Long>
