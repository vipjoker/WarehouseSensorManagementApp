package com.example.demo.sensors

import com.example.demo.utils.LogColor
import com.example.demo.utils.info
import com.example.demo.parser.ParsingResult
import com.example.demo.parser.SensorInputParser
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired

class TemperatureReceiver: SensorReceiver() {

    @Autowired
    lateinit var sensorMessagesHandler: SensorMessagesHandler

    @Autowired
    lateinit var parser: SensorInputParser

    private val logger = LogFactory.getLog(TemperatureReceiver::class.java)

    override fun handleSensorData(data: String) {
        val parsedResult = parser.parse(data)
        if(parsedResult is ParsingResult.Success){
            sensorMessagesHandler.submit(parsedResult.data, SENSOR_NAME)
        }else{
            logger.info("Wrong input $data", LogColor.RED)
        }
    }

    companion object{
        const val SENSOR_NAME="temperature"
    }
}