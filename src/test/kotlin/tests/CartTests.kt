@file:Suppress("ClassName")
package tests

import core.Setup
import factory.LoginFactory
import io.restassured.response.Response
import org.apache.http.HttpStatus
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import requests.CartRequests
import requests.LoginRequests
import requests.ProductRequests
import kotlin.test.assertEquals

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class CartTests : Setup() {

    var login = LoginRequests()
    var request = CartRequests()
    lateinit var response: Response
    lateinit var token: String

    @BeforeAll
    fun `create kart before tests and get token`() {
        val user = LoginFactory()
        response = login.login(user.loginSucceeded)
        token = response.jsonPath().get("authorization")
        val prod = ProductRequests()
        val resp: Response = prod.getAllProducts()
        val _id = resp.jsonPath().getString("produtos[0]._id")
        response = request.createCart(token, _id, 3)
    }

    @Test
    @Order(1)
    @DisplayName("cancelando carrinhos existentes")
    fun `cancel cart`() {
        response = request.cancelCart(token)
        assertEquals(HttpStatus.SC_OK, response.statusCode())
        assertEquals("Registro excluído com sucesso. Estoque dos produtos reabastecido", response.jsonPath().get("message"))
    }

    @Test
    @Order(2)
    @DisplayName("Criando um carrinho")
    fun `create new cart`() {
        val prod = ProductRequests()
        val resp: Response = prod.getAllProducts()
        val _id = resp.jsonPath().getString("produtos[0]._id")
        response = request.createCart(token, _id, 3)
        assertEquals(HttpStatus.SC_CREATED, response.statusCode())
        assertEquals("Cadastro realizado com sucesso", response.jsonPath().get("message"))
    }

    @Test
    @Order(3)
    @DisplayName("Listando todos carrinhos")
    fun `list all products`() {
        response = request.getCarts()
        assertEquals(HttpStatus.SC_OK, response.statusCode())
    }

    @Test
    @Order(4)
    @DisplayName("Concluindo compra carrinho")
    fun `submit cart`() {
        response = request.submitCart(token)
        assertEquals(HttpStatus.SC_OK, response.statusCode())
        assertEquals("Registro excluído com sucesso", response.jsonPath().get("message"))
    }
}
