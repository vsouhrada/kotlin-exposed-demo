package com.github.vsouhrada.kotlin.exposed_demo

import com.github.vsouhrada.kotlin.exposed_demo.schema.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.vendors.DatabaseDialect
import java.sql.Connection

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
  }

}

fun initOwners() {
  val pilsen = City.new { name = "Pilsen" }
  val schoeneck = City.new { name = "Schoeneck" }
  val paris = City.new { name = "Paris"}
  City.new { name = "Prague" }

  Owner.new {
    firstName = "Vaclav"
    lastName = "Souhrada"
    address = "NiceStreet 11"
    phone = "723 456 524"
    city = pilsen
  }

}