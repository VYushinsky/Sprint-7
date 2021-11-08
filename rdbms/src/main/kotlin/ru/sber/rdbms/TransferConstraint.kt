package ru.sber.rdbms

import java.sql.DriverManager

class TransferConstraint {
    private val connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/rdbms",
        "postgres",
        "postgres"
    )

    fun transfer(accountId1: Long, accountId2: Long, amount: Long) {
        connection.use{ conn-> try {
            val prepareStatement3 =
                conn.prepareStatement("UPDATE account1 SET amount = amount - $amount WHERE id = $accountId1")
            prepareStatement3.use { statement ->
                statement.executeUpdate()
            }
            val prepareStatement4 =
                conn.prepareStatement("UPDATE account1 SET amount = amount + $amount WHERE id = $accountId1")
            prepareStatement4.use { statement ->
                statement.executeUpdate()
            }
        }catch (exceprion: Exception) {
            print(exceprion)}
        }
    }
}
