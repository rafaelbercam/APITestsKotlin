@file:Suppress("ClassName")

package tests

import core.Setup
import factory.User
import io.github.serpro69.kfaker.Faker
import io.restassured.response.Response
import org.apache.http.HttpStatus
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import requests.UsersRequests
import org.junit.jupiter.api.Assertions.assertEquals

@TestMethodOrder(OrderAnnotation::class)
class UserTests : Setup() {
    private lateinit var _id: String
    private var usersRequests  = UsersRequests()
    private lateinit var response: Response

    @Test
    @Order(1)
    @DisplayName("Listando todos Usuarios")
    fun `list all users` (){
        response = usersRequests.getAllUsers()
        assertEquals(HttpStatus.SC_OK, response.statusCode())
    }

    @Test
    @Order(2)
    @DisplayName("Criando novo usuario")
    fun `create a new user` (){
        response = usersRequests.createUser(User())
        assertEquals(HttpStatus.SC_CREATED, response.statusCode())
        assertEquals("Cadastro realizado com sucesso", response.jsonPath().get("message"))
        _id = response.jsonPath().get("_id")
    }

    @Test
    @Order(3)
    @DisplayName("Listando usuário por _id")
    fun `get user by _id` (){
        response = usersRequests.getUSerById(_id)
        assertEquals(HttpStatus.SC_OK, response.statusCode())
        assertEquals(_id, response.jsonPath().get("_id"))
    }

    @Test
    @Order(4)
    @DisplayName("Alterando um usuário")
    fun `update an user` (){
        response = usersRequests.updateUser(_id, User())
        assertEquals(HttpStatus.SC_OK, response.statusCode())
        assertEquals("Registro alterado com sucesso", response.jsonPath().get("message"))
    }
}
