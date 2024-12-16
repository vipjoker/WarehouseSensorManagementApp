package com.example.demo.parser

import com.example.demo.model.SensorData
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SensorInputParserTest{

    val parser = SensorInputParser()

    @Test
    fun parserShouldNotParseWrongInput(){
        val result = parser.parse("wrong input")
        val expected: ParsingResult<SensorData> = ParsingResult.Error()
        assertEquals(result::class, expected::class)
    }

    @Test
    fun parserShouldParseCorrectInput(){
        val result = parser.parse("sensor_id=h1; value=40") as ParsingResult.Success

        assertEquals(result.data.sensorId,"h1")
    }
}