package ru.sber.rdbms

import java.sql.DriverManager
import java.sql.SQLException

class TransferPessimisticLock {
    val connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/rdbms",
        "postgres",
        "postgres"
    )

    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        connection.use { conn ->
            val autoCommit = conn.autoCommit
            try {
                conn.autoCommit = false
                val prepareStatement1 =
                    conn.prepareStatement("SELECT * FROM account1 WHERE id = $accountId1")
                prepareStatement1.executeQuery().use { statement ->
                    statement.next()
                    if (statement.getInt("amount") - amount < 0)
                        throw NoFundsException("Not enough funds")
                }
                val prepareStatement2 =
                    conn.prepareStatement("SELECT * FROM account1 WHERE id in ($accountId1, $accountId2) FOR update")
                prepareStatement2.executeQuery()
                val prepareStatement3 =
                    conn.prepareStatement("UPDATE account1 SET amount = amount - $amount WHERE id = $accountId1")
                prepareStatement3.executeUpdate()
                val prepareStatement4 =
                    conn.prepareStatement("UPDATE account1 SET amount = amount - $amount WHERE id = $accountId2")
                prepareStatement4.executeUpdate()
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