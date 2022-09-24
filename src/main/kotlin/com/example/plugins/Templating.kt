package com.example.plugins

import com.example.model.User
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.github.mustachejava.DefaultMustacheFactory
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.mustache.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureTemplating() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
    install(Mustache) {
        mustacheFactory = DefaultMustacheFactory("templates")
    }

    routing {
        get("/html-mustache") {
            call.respond(MustacheContent("index.hbs", mapOf("user" to MustacheUser(1, "user1"))))
        }
        get("/index") {
            val sampleUser = User(1,"david", "raluy", "david@raluy.fr", "1, rue des lilas")
            call.respond(MustacheContent("index.hbs", mapOf("user" to sampleUser)))
        }

        get("/users") {
            val jsonParser = JsonFactory().createParser(this.javaClass.getResourceAsStream("/database.json"))

            val users: MappingIterator<List<User>> = ObjectMapper().readValues(jsonParser, object : TypeReference<List<User>>() {})
            call.respond(users.nextValue())
        }
    }
}

data class MustacheUser(val id: Int, val name: String)
