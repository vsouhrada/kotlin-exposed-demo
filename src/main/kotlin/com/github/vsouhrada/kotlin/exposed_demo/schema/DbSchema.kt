package com.github.vsouhrada.kotlin.exposed_demo.schema

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime
import java.time.LocalDate

/**
 * @author vsouhrada
 */


object OwnerTable : IntIdTable("owner") {

  val firstName = varchar("first_name", 50)
  val lastName = varchar("last_name", 50)
  val address = varchar("address", 50)
  val city = reference("city", CityTable).uniqueIndex()
  val telephone = varchar("telephone", 20)

}


object CityTable : IntIdTable("city") {

  val name = varchar("name", 50)

}

object PetTable : IntIdTable("pet") {

  val birthDate = date("birth_date")
  val petType = reference("petType", PetTypeTable.name)
  val owner = reference("owner", OwnerTable)

}

object PetTypeTable : Table("pet_type") {

  val name = varchar("name", 20).primaryKey().entityId()

}

object VisitTable : IntIdTable("visit") {

  val date = date("date")
  val description = varchar("description", 255)
  val pet = reference("pet", PetTable)

}

// Class Definition
class City(id: EntityID<Int>) : IntEntity(id) {
  companion object : IntEntityClass<City>(CityTable)

  var name by CityTable.name

  override fun toString(): String {
    return "City{id=$id, name=$name}"
  }

}

class Owner(id: EntityID<Int>) : IntEntity(id) {
  companion object : IntEntityClass<Owner>(OwnerTable)

  var firstName by OwnerTable.firstName
  var lastName by OwnerTable.lastName
  var address by OwnerTable.address
  var phone by OwnerTable.telephone
  var city by City referencedOn OwnerTable.city

  override fun toString(): String {
    return "Owner{id=$id, firstName=$firstName, lastName=$lastName, address=$address, phone=$phone, city=$city"
  }
}