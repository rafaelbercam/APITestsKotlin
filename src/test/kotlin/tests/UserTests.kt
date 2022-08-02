@file:Suppress("ClassName")
package tests

import core.Setup
import factory.User
import io.github.serpro69.kfaker.Faker
import io.restassured.response.Response
import org.apache.http.HttpStatus
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import requests.UsersRequests
import org.junit.jupiter.api.Assertions.assertEquals

@TestMethodOrder(OrderAnnotation::class)
class UserTests: Setup() {

    private var _id: String = ""
    private var request  = UsersRequests()
    private lateinit var response: Response
    private var user = User()

    @Test
    @Order(1)
    @DisplayName("Listando todos Usuarios")
    fun `list all users` (){
        response = request.getAllUsers()
        assertEquals(HttpStatus.SC_OK, response.statusCode())
    }

    @Test
    @Order(2)
    @DisplayName("Criando novo usuario")
    fun `create a new user` (){
        user = User()
        response = request.createUser(user)
        assertEquals(HttpStatus.SC_CREATED, response.statusCode())
        assertEquals("Cadastro realizado com sucesso", response.jsonPath().get("message"))
        _id = response.jsonPath().get("_id")
    }

    @Test
    @Order(3)
    @DisplayName("Listando usuário por _id")
    fun `get user by _id` (){
        response = request.getUSerById(_id)
        assertEquals(HttpStatus.SC_OK, response.statusCode())
        assertEquals(_id, response.jsonPath().get("_id"))
    }

    @Test
    @Order(4)
    @DisplayName("Alterando um usuário")
    fun `update an user` (){
        user = User()
        response = request.updateUser(_id, user)
        assertEquals(HttpStatus.SC_OK, response.statusCode())
        assertEquals("Registro alterado com sucesso", response.jsonPath().get("message"))
    }
}