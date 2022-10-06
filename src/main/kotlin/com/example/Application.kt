package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.github.mustachejava.DefaultMustacheFactory
import io.ktor.server.application.*
import io.ktor.server.mustache.*

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {

        configureHTTP()
        configureRouting()
    }.start(wait = true)
}
