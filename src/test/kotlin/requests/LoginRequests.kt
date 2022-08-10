package requests

import core.Setup
import factory.Login
import io.qameta.allure.Step
import io.qameta.allure.restassured.AllureRestAssured
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

    @Step("login request /login")
    open fun login(login: Login): Response {
        val response =
            Given {
                spec(requestSpecification)
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
