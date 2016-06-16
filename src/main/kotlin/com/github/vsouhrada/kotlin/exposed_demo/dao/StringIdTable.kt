package com.github.vsouhrada.kotlin.exposed_demo.dao

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.sql.Column

/**
 * @author vsouhrada
 */
open class StringIdTable(name: String = "", idColumnName: String = "id", idColumnSize: Int = 32) : IdTable<String>(name) {
   override val id: Column<EntityID<String>> = varchar(idColumnName, idColumnSize).entityId()
}

abstract class StringEntity(id: EntityID<String>) : Entity<String>(id)

abstract class StringEntityClass<E: StringEntity>(table: IdTable<String>) : EntityClass<String, E> (table)