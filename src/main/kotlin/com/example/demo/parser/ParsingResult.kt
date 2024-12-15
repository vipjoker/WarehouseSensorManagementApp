package com.example.demo.parser

sealed class ParsingResult<T> {
    class Success<T>(val data:T):ParsingResult<T>()
    class Error<T>:ParsingResult<T>()
}

