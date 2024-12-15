package com.example.demo.service

import com.example.demo.model.SensorType
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class CentralService(
    @Value("\${humidity.treshold}")
    var humidityTreshold: Double,
    @Value("\${temprature.teshold}")
    var temperatureTreshold: Double
) {

    private var warehouses = mutableListOf<WareHouseService>()

    @Autowired
    private lateinit var centralServiceMessagesHandler: CentralServiceMessagesHandler

    @PostConstruct
    private fun setup() {
        centralServiceMessagesHandler.observe()
            .filter {
                when (it.sensorType) {
                    SensorType.HUMIDITY -> it.sensorData.value > humidityTreshold
                    SensorType.TEMPERATURE -> it.sensorData.value > temperatureTreshold
                }
            }.subscribe { warehouseSensorData ->
                warehouses.find { it.name == warehouseSensorData.warehouseName }?.activateAlarm(
                    buildString {
                        append("Sensor with id ${warehouseSensorData.sensorData.sensorId} ")
                        append("in warehouse ${warehouseSensorData.warehouseName} has exceeded ")
                        append("${if (warehouseSensorData.sensorType == SensorType.HUMIDITY) "humidity" else "temperature"} ")
                        append("value to ${warehouseSensorData.sensorData.value}")
                    }
                )
            }
    }

    fun registerService(wareHouseService: WareHouseService) {
        warehouses.add(wareHouseService)
    }
}