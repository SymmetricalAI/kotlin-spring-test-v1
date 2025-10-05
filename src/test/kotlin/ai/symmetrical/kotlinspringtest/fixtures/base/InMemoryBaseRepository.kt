package ai.symmetrical.kotlinspringtest.fixtures.base

import org.bson.types.ObjectId
import kotlin.reflect.full.instanceParameter
import kotlin.reflect.full.memberFunctions

abstract class InMemoryBaseRepository<T> : Clearable {
    val store = mutableMapOf<ObjectId, T>()

    fun save(it: T) = saveWithCopy(it).let { }

    fun findById(id: ObjectId): T? = store[id]

    fun deleteById(id: ObjectId): Unit = store.remove(id).let { }

    fun findAll(): List<T> = store.values.toList()

    fun deleteAll(): Unit = clear()

    override fun clear() = store.clear()

    private fun getId(input: T): ObjectId = input!!::class::members.get().first { it.name == "id" }.call(input) as ObjectId

    private fun saveWithCopy(it: T): T {
        val dataClass = it!!::class
        require(dataClass.isData) { "Type of object to copy must be a data class" }
        val copyFunction = dataClass.memberFunctions.first { it.name == "copy" }
        val parameters =
            buildMap {
                put(copyFunction.instanceParameter!!, it)
            }
        store[getId(it)] = copyFunction.callBy(parameters) as T
        return it
    }
}
