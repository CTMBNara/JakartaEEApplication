package repository

import database.repository.CrudRepository
import domain.Program

interface ProgramRepository : CrudRepository<Program, Long>
