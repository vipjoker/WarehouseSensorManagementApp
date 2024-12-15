package com.example.demo.parser

import com.example.demo.model.SensorData

class SensorInputParser {
    private val regex = Regex(
        pattern = "sensor_id=[A-Za-z0-9]*; value=[+-]?([0-9]*[.])?[0-9]+",
        options = setOf(RegexOption.IGNORE_CASE)
    )

    fun parse(rawString: String): ParsingResult<SensorData> {
        return if (regex.matches(rawString)) {
            val id = rawString.substringAfter("=").substringBefore(';')
            val value = rawString.substringAfterLast("=").toDouble()
            ParsingResult.Success(SensorData(id, value))
        } else {
             ParsingResult.Error()
        }
    }


}