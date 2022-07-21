package requests

import core.Setup
import factory.Login
import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

open class LoginRequests : Setup() {

    open fun login (login: Login) : Response {

        val response =
            Given {
                spec(requestSpecification)
                    .filter(RequestLoggingFilter(LogDetail.ALL))
                    .filter(ResponseLoggingFilter(LogDetail.ALL))
                    .body(Json.encodeToString(login))
            } When {
                post("/login")
            } Then {

            } Extract {
                response()
            }
        return response
    }
}