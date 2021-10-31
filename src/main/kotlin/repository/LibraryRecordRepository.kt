package repository

import database.repository.CrudRepository
import domain.LibraryRecord

interface LibraryRecordRepository : CrudRepository<LibraryRecord, Long>
