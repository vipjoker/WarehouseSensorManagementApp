package com.example.demo.service

import com.example.demo.model.WarehouseMeasurement
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class CentralServiceMessagesHandlerImpl: CentralServiceMessagesHandler {
    private val subject = PublishSubject.create<WarehouseMeasurement>()
    override fun submit(warehouseMeasurement: WarehouseMeasurement) {
        subject.onNext(warehouseMeasurement)
    }

    override fun observe(): Observable<WarehouseMeasurement> {
        return subject
    }
}