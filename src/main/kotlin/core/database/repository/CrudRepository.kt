package core.database.repository

interface CrudRepository<Entity, Id> {
    suspend fun save(entity: Entity)
    suspend fun saveAll(entities: Iterable<Entity>)

    suspend fun findAll(): Collection<Entity>
    suspend fun findAllById(ids: Iterable<Id>): Collection<Entity>
    suspend fun findById(id: Id): Entity?

    suspend fun delete(entity: Entity)
    suspend fun deleteAll()
    suspend fun deleteAll(entities: Iterable<Entity>)
    suspend fun deleteAllById(ids: Iterable<Id>)
    suspend fun deleteById(id: Id)

    suspend fun count(): Long
    suspend fun existsById(id: Id): Boolean
}
