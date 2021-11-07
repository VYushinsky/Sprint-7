package com.Sber.MVC



import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan




@ServletComponentScan
@SpringBootApplication
class ProjectApplication/*(
	private val bookUserRepository: BookUserRepository
) : CommandLineRunner {
	override fun run(vararg args: String?) {
		val user = User(
			username = "u",
			password = "u",
			role = Role.USER
		)
		val moderator = User(
			username = "m",
			password = "m",
			role = Role.MODERATOR
		)
		val admin = User(
			username = "a",
			password = "a",
			role = Role.ADMIN
		)
		bookUserRepository.saveAll(listOf(user, moderator, admin))
	}
}*/

fun main(args: Array<String>) {
	runApplication<ProjectApplication>(*args)
}

