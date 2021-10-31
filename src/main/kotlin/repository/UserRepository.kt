package repository

import database.repository.CrudRepository
import domain.User

interface UserRepository : CrudRepository<User, Long>
