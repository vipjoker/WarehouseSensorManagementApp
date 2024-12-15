package com.example.demo.sensors

import org.springframework.messaging.Message
import org.springframework.messaging.MessageHandler

abstract class SensorReceiver: MessageHandler {

    override fun handleMessage(message: Message<*>) {
        try{
            handleSensorData(message.payload as String)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    abstract fun handleSensorData(data:String)

}