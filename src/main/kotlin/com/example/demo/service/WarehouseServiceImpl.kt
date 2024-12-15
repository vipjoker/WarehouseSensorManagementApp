package com.example.demo.service

import com.example.demo.model.SensorData
import com.example.demo.model.SensorType
import com.example.demo.model.WarehouseMeasurement
import com.example.demo.utils.LogColor
import com.example.demo.sensors.SensorMessagesHandler
import com.example.demo.utils.info
import com.example.demo.sensors.HumidityReceiver
import com.example.demo.sensors.TemperatureReceiver
import org.apache.commons.logging.LogFactory

class WarehouseServiceImpl(
    override val name: String,
    sensorMessagesHandler: SensorMessagesHandler,
    private val centralServiceMessagesHandler: CentralServiceMessagesHandler
): WareHouseService {


    private val registeredSensorIds = mutableListOf<String>()

    init {
        sensorMessagesHandler.observe(HumidityReceiver.SENSOR_NAME).subscribe {
            if(registeredSensorIds.contains(it.sensorId)){
                sendDataToCentralService(it, SensorType.HUMIDITY)
            }
        }

        sensorMessagesHandler.observe(TemperatureReceiver.SENSOR_NAME).subscribe {
            if(registeredSensorIds.contains(it.sensorId)){
                sendDataToCentralService(it, SensorType.TEMPERATURE)
            }
        }
    }

    private fun sendDataToCentralService(sensorData: SensorData, type:SensorType){
        centralServiceMessagesHandler.submit(WarehouseMeasurement(name,sensorData,type))
    }

    fun registerSensorIds(vararg sensorIds:String){
        registeredSensorIds.addAll(sensorIds)
    }

    private val logger = LogFactory.getLog(WarehouseServiceImpl::class.java)



    override fun activateAlarm(warning:String) {
        logger.info("ALARM : $warning", LogColor.RED)
    }
}