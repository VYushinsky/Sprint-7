package com.Sber.MVC

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@ServletComponentScan
@SpringBootApplication
class ProjectApplication
fun main(args: Array<String>) {
	runApplication<ProjectApplication>(*args)
}

