package com.example.demo
import org.springframework.boot.Banner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import java.util.concurrent.CountDownLatch

@SpringBootApplication
class WarehouseSensorManagementApp

fun main(args: Array<String>) {
	SpringApplicationBuilder(WarehouseSensorManagementApp::class.java)
		.web(WebApplicationType.NONE)
		.bannerMode(Banner.Mode.OFF)
		.run()
	val latch = CountDownLatch(1)
	latch.await()
}

