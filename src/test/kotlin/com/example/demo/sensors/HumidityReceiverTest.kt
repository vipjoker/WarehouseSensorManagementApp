package com.example.demo.sensors

import com.example.demo.parser.SensorInputParser
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HumidityReceiverTest{
    val humidityReceiver = HumidityReceiver()
    val  handler:SensorMessagesHandler = mockk()

    @BeforeEach
    fun setup(){
        humidityReceiver.sensorMessagesHandler = handler
        humidityReceiver.parser = SensorInputParser()
    }

    @Test
    fun humidityReceiverShouldNotProceedWrongInput(){
        humidityReceiver.handleSensorData("wrong data")
        verify (exactly = 0){ handler.submit(any(),any()) }
    }


    @Test
    fun humidityReceiverShouldProceedCorrectInput(){
        every { handler.submit(any(),any()) } just runs
        humidityReceiver.handleSensorData("sensor_id=h1; value=40")
        verify (exactly = 1){ handler.submit(any(),any()) }
    }

}

