package core

import com.google.gson.Gson
import factory.LoginData
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.config.LogConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.filter.log.LogDetail
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import java.io.File


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class Setup {

    private val pathProject: String = System.getProperty("user.dir")
    var loginJson = File("$pathProject/src/test/kotlin/resources/login.json").readText(Charsets.UTF_8)
    val loginData: LoginData = Gson().fromJson(loginJson, LoginData::class.java)
    val gson = Gson()

    companion object {
        lateinit var requestSpecification: RequestSpecification
    }

    @BeforeAll
    fun setup(){
        val logConfig = LogConfig.logConfig()
            .enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL)
        val config = RestAssuredConfig.config().logConfig(logConfig)

        requestSpecification = RequestSpecBuilder()
            .setBaseUri("https://serverest.dev")
            .setContentType(ContentType.JSON)
            .setRelaxedHTTPSValidation()
            .setConfig(config)
            .build()
    }

    @AfterAll
    fun tearDown(){
        RestAssured.reset()
    }
}