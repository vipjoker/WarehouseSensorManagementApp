package com.example.demo.config

import com.example.demo.parser.SensorInputParser
import com.example.demo.sensors.*
import com.example.demo.service.CentralService
import com.example.demo.service.CentralServiceMessagesHandler
import com.example.demo.service.CentralServiceMessagesHandlerImpl
import com.example.demo.service.WarehouseServiceImpl
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter

@Configuration
class ServiceConfiguration {
    val logger: Log = LogFactory.getLog(ServiceConfiguration::class.java)


    @Bean(name = ["temperature"])
    fun createTemperatureReceiver():SensorReceiver{
        return TemperatureReceiver()
    }

    @Bean(name = ["humidity"])
    fun createHumidityReceiver():SensorReceiver{
        return HumidityReceiver()
    }

    @Bean
    fun createHumidityHandler(@Value("\${humidity.sensor.port}") port:Int,
                              @Qualifier("humidity") receiver: SensorReceiver
                              ): IntegrationFlow {
        return createIntegrationFlow(port,receiver)
    }

    @Bean
    fun createTemperatureHandler(@Value("\${temperature.sensor.port}") port:Int,
        @Qualifier("temperature") receiver: SensorReceiver

    ): IntegrationFlow {
        return createIntegrationFlow(port,receiver)
    }

    private fun createIntegrationFlow(port:Int, receiver: SensorReceiver):IntegrationFlow{
        return IntegrationFlow
            .from( UnicastReceivingChannelAdapter(port))
            .transform { bytes: ByteArray -> String(bytes) }
            .handle(receiver)
            .get()
    }


    @Bean
    fun provideMessagesHandler(): SensorMessagesHandler {
        return SensorMessagesHandlerImpl()
    }

    @Bean
    fun provideInputParser():SensorInputParser{
        return SensorInputParser()
    }

    @Bean
    fun provideCentralServiceMessagesHandler():CentralServiceMessagesHandler{
        return CentralServiceMessagesHandlerImpl()
    }


    @Bean
    fun configureMockWareHouses(service: CentralService,sensorMessagesHandler: SensorMessagesHandler,
                                centralServiceMessagesHandler: CentralServiceMessagesHandler) = ApplicationRunner {
       val warehouse1 = WarehouseServiceImpl("WarehouseONE",sensorMessagesHandler,centralServiceMessagesHandler)
        warehouse1.registerSensorIds("t1", "h1")

        val warehouse2 = WarehouseServiceImpl("WarehouseTWO",sensorMessagesHandler,centralServiceMessagesHandler)
        warehouse2.registerSensorIds("t2", "h2")

        val warehouse3 = WarehouseServiceImpl("WarehouseTHREE",sensorMessagesHandler,centralServiceMessagesHandler)
        warehouse3.registerSensorIds("t3", "h3")

        service.registerService(warehouse1)
        service.registerService(warehouse2)
        service.registerService(warehouse3)

    }

}