@file:Suppress("ClassName")

package tests

import core.Setup
import factory.UserFactory
import io.qameta.allure.Description
import io.restassured.response.Response
import org.apache.http.HttpStatus
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import requests.UsersRequests
import kotlin.test.assertEquals

@TestMethodOrder(OrderAnnotation::class)
class UserTests : Setup() {

    private lateinit var _id: String
    var request = UsersRequests()
    lateinit var response: Response

    @Test
    @Order(1)
    @Description("Listando todos Usuários")
    fun `list all users`() {
        response = request.getAllUsers()
        assertEquals(HttpStatus.SC_OK, response.statusCode())
    }

    @Test
    @Order(2)
    @Description("Criando novo usuário")
    fun `create a new user`() {
        val user = UserFactory()
        response = request.createUser(user.createUser)
        assertEquals(HttpStatus.SC_CREATED, response.statusCode())
        assertEquals("Cadastro realizado com sucesso", response.jsonPath().get("message"))
        _id = response.jsonPath().get("_id")
    }

    @Test
    @Order(3)
    @Description("Listando usuário por _id")
    fun `get user by _id`() {
        response = request.getUSerById(_id)
        assertEquals(HttpStatus.SC_OK, response.statusCode())
        assertEquals(_id, response.jsonPath().get("_id"))
    }

    @Test
    @Order(4)
    @Description("Alterando um usuário")
    fun `update an user`() {
        val user = UserFactory()
        response = request.updateUser(_id, user.createUser)
        assertEquals(HttpStatus.SC_OK, response.statusCode())
        assertEquals("Registro alterado com sucesso", response.jsonPath().get("message"))
    }
}
