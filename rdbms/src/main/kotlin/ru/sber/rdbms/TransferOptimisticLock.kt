package ru.sber.rdbms

import java.sql.DriverManager
import java.sql.SQLException

class TransferOptimisticLock {
    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        val connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/rdbms",
            "postgres",
            "postgres"
        )

        connection.use { conn ->
            val autoCommit = conn.autoCommit
            var version1 = 0
            var version2 = 0
            var balance = 0

            try {
                conn.autoCommit = false
                val prepareStatement1 = conn.prepareStatement(
                    "SELECT * " +
                            "FROM account1 " +
                            "WHERE id = ?")
                prepareStatement1.use { statement ->
                    statement.setLong(1, accountId1)
                    statement.executeQuery().use {
                        it.next()
                        version1 = it.getInt("version")
                        balance = it.getInt("amount")
                    }
                }

                if (balance - amount < 0)
                    throw NoFundsException("Not enough funds")

                val prepareStatement2 = conn.prepareStatement(
                    "SELECT * " +
                            "FROM account1 " +
                            "WHERE id = ?")
                prepareStatement2.use { statement ->
                    statement.setLong(1, accountId2)
                    statement.executeQuery().use {
                        it.next()
                        version2 = it.getInt("version")
                    }
                }

                val prepareStatement3 = conn.prepareStatement(
                    "UPDATE account1 " +
                            "SET amount = amount - ?, version = version + 1 " +
                            "WHERE id = ? AND version = ?")
                prepareStatement3.use { statement ->
                    statement.setLong(1, amount)
                    statement.setLong(2, accountId1)
                    statement.setInt(3, version1)
                    val updatedRows = statement.executeUpdate()
                    if (updatedRows == 0)
                        throw SQLException("Concurrent update")
                }

                val prepareStatement4 =
                    conn.prepareStatement(
                        "UPDATE account1 " +
                                "SET amount = amount + ?, version = version + 1 " +
                                "WHERE id = ? AND version = ?")
                prepareStatement4.use { statement ->
                    statement.setLong(1, amount)
                    statement.setLong(2, accountId2)
                    statement.setInt(3, version2)
                    val updatedRows = statement.executeUpdate()
                    if (updatedRows == 0)
                        throw SQLException("Concurrent update")
                }
                conn.commit()
            } catch (exception: SQLException) {
                println(exception.message)
                exception.printStackTrace()
                conn.rollback()
            } finally {
                conn.autoCommit = autoCommit
            }
        }
    }
}