package com.example.plugins

import com.example.model.User
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.mustache.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.util.pipeline.*

fun Application.configureRouting() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
    routing {
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }

        get("/all-users") {
            val userList = users()
            call.respond(userList)
        }

        get("/users") {
            var userList = users()
            val firstNameFilter = call.request.queryParameters["first_name"]
            if (firstNameFilter != null) {
                userList = userList.filter { user -> user.first_name.contains(firstNameFilter, true) }
            }

            val lastNameFilter = call.request.queryParameters["last_name"]
            if (lastNameFilter != null) {
                userList = userList.filter { user -> user.last_name.contains(lastNameFilter, true) }
            }

            val emailFilter = call.request.queryParameters["email"]
            if (emailFilter != null) {
                userList = userList.filter { user -> user.email.contains(emailFilter, true) }
            }

            val addressFilter = call.request.queryParameters["address"]
            if (addressFilter != null) {
                userList = userList.filter { user -> user.address.contains(addressFilter, true) }
            }

            val searchFilter = call.request.queryParameters["search"]
            if (searchFilter != null) {
                userList = userList.filter { user ->
                    user.address.contains(searchFilter, true) ||
                            user.first_name.contains(searchFilter, true) ||
                            user.last_name.contains(searchFilter, true) ||
                            user.email.contains(searchFilter, true)
                }
            }
            call.respond(userList)
        }

    }

}

private fun PipelineContext<Unit, ApplicationCall>.users(): List<User> {
    val jsonParser = JsonFactory().createParser(this.javaClass.getResourceAsStream("/database.json"))

    val users: MappingIterator<List<User>> = ObjectMapper().readValues(jsonParser, object : TypeReference<List<User>>() {})
    return users.nextValue()
}


