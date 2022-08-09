package core

import factory.UserFactory
import io.github.cdimascio.dotenv.dotenv
import io.qameta.allure.restassured.AllureRestAssured
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.config.LogConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.http.ContentType
import io.restassured.response.Response
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import requests.UsersRequests


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class Setup {

    val dotenv = dotenv()

    companion object {
        lateinit var requestSpecification: RequestSpecification
    }

    @BeforeAll
    fun setup() {
        val logConfig = LogConfig.logConfig()
            .enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL)
        val config = RestAssuredConfig.config().logConfig(logConfig)

        requestSpecification = RequestSpecBuilder()
            .setBaseUri(dotenv["DEV"])
            .setContentType(ContentType.JSON)
            .setRelaxedHTTPSValidation()
            .setConfig(config)
            .addFilter(RequestLoggingFilter(LogDetail.ALL))
            .addFilter(ResponseLoggingFilter(LogDetail.ALL))
            .build()
    }

    @AfterAll
    fun tearDown() {
        RestAssured.reset()
    }

    @BeforeAll
    fun `create pre user`() {
        val user = UserFactory()
        val request = UsersRequests()
        val response: Response = request.createUser(user.createPreUser)
        val message: String = response.jsonPath().get("message")
        if(response.statusCode == 201){
            println("*** USUÁRIO FOI CRIADO ***")
        } else if (message == "Este email já está sendo usado"){
            println("*** USUÁRIO JÁ FOI CRIADO ***")
        }
    }
}
