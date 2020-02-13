package com.example.assignment.core

import java.util.logging.Logger
import javax.inject.Inject

class ExchangeLogService : LogService {

    @Inject
    constructor() {
    }

    override fun log(className: String, text: String) {
        Logger.getLogger(className).warning(text)
    }
}

interface LogService {
    fun log(className: String, text: String)
}