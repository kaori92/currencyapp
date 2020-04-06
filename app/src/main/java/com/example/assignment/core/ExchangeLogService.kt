package com.example.assignment.core

import java.util.logging.Logger

class ExchangeLogService : LogService {

    override fun log(className: String, text: String) {
        Logger.getLogger(className).warning(text)
    }
}

interface LogService {
    fun log(className: String, text: String)
}