@file:Suppress("ClassName")

package tests

import core.Setup
import factory.User
import io.restassured.response.Response
import org.apache.http.HttpStatus
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import requests.LoginRequests
import requests.UsersRequests
import kotlin.test.assertEquals

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class LoginTests : Setup() {

    private var login = LoginRequests()
    private lateinit var response: Response
    private var usersRequests  = UsersRequests()
    private val user = User()

    @Test
    @Order(1)
    @DisplayName("Login bem sucedido")
        assertEquals(HttpStatus.SC_OK, response.statusCode)
        assertEquals("Login realizado com sucesso", response.jsonPath().get("message"))
    }

    @Test
    @Order(2)
    @DisplayName("Login com falha")
    fun `login failed` () {
        response = login.loginRequest(loginData.wrong_email, loginData.password)
        assertEquals(HttpStatus.SC_UNAUTHORIZED, response.statusCode)
        assertEquals("Email e/ou senha inválidos", response.jsonPath().get("message"))
    }

    @Test
    @Order(3)
    @DisplayName("Email obrigatório")
    fun `login failed - email required` () {
        response = login.loginRequest(loginData.email_empty, loginData.password)
        assertEquals(HttpStatus.SC_BAD_REQUEST, response.statusCode)
        assertEquals("email não pode ficar em branco", response.jsonPath().get("email"))
    }

    @Test
    @Order(4)
    @DisplayName("Password obrigatório")
    fun `login failed - password required` () {
        response = login.loginRequest(loginData.email, loginData.password_empty)
        assertEquals(HttpStatus.SC_BAD_REQUEST, response.statusCode)
        assertEquals("password não pode ficar em branco", response.jsonPath().get("password"))
    }
}
