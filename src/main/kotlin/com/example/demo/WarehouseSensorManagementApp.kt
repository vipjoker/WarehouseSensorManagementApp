package com.example.demo
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.Banner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler
import org.springframework.integration.support.MessageBuilder
import java.util.concurrent.ThreadLocalRandom
import kotlin.concurrent.thread


@SpringBootApplication
class WarehouseSensorManagementApp {

	@Bean
	fun runner(): ApplicationRunner = ApplicationRunner {
		thread {
			while (true) {
				Thread.sleep(1000)
				val handler =
					UnicastSendingMessageHandler("localhost", 3355)
				val payload = "sensor_id=h1; value=${ThreadLocalRandom.current().nextDouble(0.0,100.0)}"
				handler.handleMessage(MessageBuilder.withPayload(payload).build())
			}
		}

	}
}

fun main(args: Array<String>) {
	SpringApplicationBuilder(WarehouseSensorManagementApp::class.java)
		.web(WebApplicationType.NONE)
		.bannerMode(Banner.Mode.OFF)
		.run()
}

