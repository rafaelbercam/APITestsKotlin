package requests

import core.Setup
import factory.User
import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response


open class UsersRequests : Setup() {

    open fun getAllUsers(): Response {
        val response =
            Given {
                spec(requestSpecification)
                    .filter(RequestLoggingFilter(LogDetail.ALL))
                    .filter(ResponseLoggingFilter(LogDetail.ALL))
            } When {
                get("/usuarios")
            } Then {
            } Extract {
                response()
            }
        return response
    }

    open fun getUSerById(_id: String): Response {
        val response =
            Given {
                spec(requestSpecification)
                    .filter(RequestLoggingFilter(LogDetail.ALL))
                    .filter(ResponseLoggingFilter(LogDetail.ALL))
            } When {
                get("/usuarios/$_id")
            } Then {
            } Extract {
                response()
            }
        return response
    }

    open fun createUser(user: User): Response {
        val response =
            Given {
                spec(requestSpecification)
                    .filter(RequestLoggingFilter(LogDetail.ALL))
                    .filter(ResponseLoggingFilter(LogDetail.ALL))
                    .body(gson.toJson(user))
            } When {
                post("/usuarios")
            } Then {
            } Extract {
                response()
            }
        return response
    }

    open fun updateUser(_id: String, user: User): Response {
        val response =
            Given {
                spec(requestSpecification)
                    .filter(RequestLoggingFilter(LogDetail.ALL))
                    .filter(ResponseLoggingFilter(LogDetail.ALL))
                    .body(gson.toJson(user))
            } When {
                put("/usuarios/$_id")
            } Then {
            } Extract {
                response()
            }
        return response
    }

    open fun deleteUser(_id: String): Response {
        val response =
            Given {
                spec(requestSpecification)
                    .filter(RequestLoggingFilter(LogDetail.ALL))
                    .filter(ResponseLoggingFilter(LogDetail.ALL))
            } When {
                delete("/usuarios/$_id")
            } Then {
            } Extract {
                response()
            }
        return response
    }
}
