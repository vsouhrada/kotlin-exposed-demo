package com.github.vsouhrada.kotlin.exposed_demo

import com.github.vsouhrada.kotlin.exposed_demo.schema.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

/**
 * @author vsouhrada
 */

fun main(args: Array<String>) {
  Database.connect(
          url = "jdbc:h2:mem:exposed_demo",
          driver = "org.h2.Driver"
  )

  initDatabaseStructure()
}

fun initDatabaseStructure() {

  transaction {
    logger.addLogger(StdOutSqlLogger())

    SchemaUtils.create(CityTable, OwnerTable, PetTypeTable, PetTable, VisitTable)

    initOwners()

    println("---------------------")
    println("Cities: ${City.all().joinToString { it.name }}")
    println("Owners: ${Owner.all().joinToString { it.toString() }}")
    println("PetTypes: ${PetType.all().joinToString { it.toString() }}")
    println("Pets: ${Pet.all().joinToString { it.toString() }}")
  }

}

fun initOwners() {
  val pilsen = City.new { name = "Pilsen" }
  val schoeneck = City.new { name = "Schoeneck" }
  val paris = City.new { name = "Paris" }
  City.new { name = "Prague" }

  val owner1 = Owner.new {
    firstName = "Vaclav"
    lastName = "Souhrada"
    address = "NiceStreet 11"
    phone = "723 456 524"
    city = pilsen
  }

  val owner2 = Owner.new {
    firstName = "Stephan"
    lastName = "Boese"
    address = "Friedrich Strase 23"
    phone = "722 455 123"
    city = schoeneck
  }

  val dog = PetType.new(id = "Dog") { isEnabled = true }
  val cat = PetType.new(id = "Cat") { isEnabled = true }

  Pet.new {
    birthDate = DateTime.now()
    petType = dog
    owner = owner1
  }

  Pet.new {
    birthDate = DateTime.now()
    petType = cat
    owner = owner2
  }

}