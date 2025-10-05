package ai.symmetrical.kotlinspringtest.infrastructure.kafka

import ai.symmetrical.kotlinspringtest.fixtures.base.Clearable

class InMemoryGenericKafkaPublisher :
    GenericKafkaPublisher,
    Clearable {
    val events: MutableMap<String, MutableList<Any>> = mutableMapOf()

    override fun publish(
        topic: String,
        key: String?,
        message: Any,
    ) {
        events[topic]?.add(message) ?: mutableListOf(message).also { events[topic] = it }
    }

    override fun clear() = events.clear()

    fun <T> getEventsOfTypeFromTopic(
        type: Class<*>,
        topic: String,
    ): List<T> =
        events[topic]?.filter { event ->
            type.isAssignableFrom(event::class.java)
        } as List<T> ?: emptyList()

    fun getEventsOfTypes(vararg types: Class<*>): List<Any> =
        events.values.flatten().filter { event ->
            types.any { type ->
                type.isAssignableFrom(event::class.java)
            }
        }
}
