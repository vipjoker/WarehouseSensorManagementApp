package com.example.demo.sensors

import com.example.demo.model.SensorData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class SensorMessagesHandlerImpl : SensorMessagesHandler {
    private val topicsMap = mutableMapOf<String, PublishSubject<SensorData>>()

    override fun submit(sensorData: SensorData, topic: String) {
       getEmitter(topic).onNext(sensorData)
    }

    override fun observe(topic: String): Observable<SensorData> {
        return getEmitter(topic)
    }

    private fun getEmitter(topic: String): PublishSubject<SensorData> {
        topicsMap[topic]?.let {
            return it
        } ?: run {
            return PublishSubject.create<SensorData>().also {
                topicsMap[topic] = it
            }
        }

    }
}