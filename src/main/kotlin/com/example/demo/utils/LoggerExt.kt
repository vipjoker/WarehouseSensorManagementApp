package com.example.demo.utils

import org.apache.commons.logging.Log

fun Log.info(text:String, color: LogColor){
    when(color){
        LogColor.GREEN ->this.info("\u001B[32m$text\u001B[0m");
        LogColor.RED ->this.info("\u001B[31m$text\u001B[0m");
        else->this.info(text)
    }
}
enum class LogColor{
    GREEN,RED
}