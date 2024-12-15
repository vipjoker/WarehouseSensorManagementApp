package com.example.demo.service

import com.example.demo.model.WarehouseMeasurement
import io.reactivex.rxjava3.core.Observable

interface CentralServiceMessagesHandler {
    fun submit(warehouseMeasurement: WarehouseMeasurement)

    fun observe(): Observable<WarehouseMeasurement>
}