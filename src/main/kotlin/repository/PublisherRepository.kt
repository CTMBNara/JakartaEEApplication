package repository

import database.repository.CrudRepository
import domain.Publisher

interface PublisherRepository : CrudRepository<Publisher, Long>
