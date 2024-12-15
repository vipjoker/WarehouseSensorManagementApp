package com.example.demo.sensors

import com.example.demo.model.SensorData
import io.reactivex.rxjava3.core.Observable

interface SensorMessagesHandler {

    fun submit(sensorData: SensorData, topic:String)

    fun observe(topic: String):Observable<SensorData>
}