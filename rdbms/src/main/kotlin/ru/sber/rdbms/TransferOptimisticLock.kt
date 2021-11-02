package ru.sber.rdbms

import java.sql.DriverManager
import java.sql.SQLException

class TransferOptimisticLock1 {
    val connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/db",
        "postgres",
        "postgres"
    )

    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        connection.use { conn ->
            val autoCommit = conn.autoCommit
            try {conn.autoCommit = false
                val prepareStatement1 = conn.prepareStatement("SELECT amount FROM account1 WHERE id = ${accountId1} FOR update")
                prepareStatement1.use { statement ->
                    val result =  statement.executeUpdate()
                    if(result<amount) throw NoFundsException("Not enough funds")}
                val prepareStatement2 = conn.prepareStatement("SELECT amount FROM account1 WHERE id = ${accountId2} FOR update")
                prepareStatement2.use { statement ->
                    statement.executeUpdate()}
                val prepareStatement3 = conn.prepareStatement("UPDATE account1 SET amount = amount - $amount WHERE id = ${accountId1}")
                prepareStatement3.use { statement ->
                    statement.executeQuery()}
                val prepareStatement4 = conn.prepareStatement("UPDATE account1 SET amount = amount - $amount WHERE id = ${accountId2}")
                prepareStatement4.use { statement ->
                    statement.executeQuery()}
                conn.commit()
            } catch (exception: SQLException) {
                println(exception.message)
                conn.rollback()
            } finally {
                conn.autoCommit = autoCommit
            }
        }
    }
}
