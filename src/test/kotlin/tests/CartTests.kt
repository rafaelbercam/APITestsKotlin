@file:Suppress("ClassName")
package tests

import core.Setup
import factory.User
import io.restassured.response.Response
import org.apache.http.HttpStatus
import org.junit.jupiter.api.*
import requests.CartRequests
import requests.LoginRequests
import requests.ProductRequests
import requests.UsersRequests
import kotlin.test.assertEquals

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class CartTests: Setup() {

    private var login = LoginRequests()
    private var request = CartRequests()
    private lateinit var response: Response
    private lateinit var token : String
    private var usersRequests  = UsersRequests()
    private val user = User()

    @BeforeAll
    fun `create kart before tests and get token`(){
        usersRequests.createUser(user)
        response = login.loginRequest(user.email, user.password)
        token = response.jsonPath().get("authorization")
        val prod = ProductRequests();
        val resp: Response = prod.getAllProducts()
        val _id = resp.jsonPath().getString("produtos[0]._id")
        response = request.createCart(token,_id, 3)
    }

    @Test
    @Order(1)
    @DisplayName("cancelando carrinhos existentes")
    fun `cancel cart` (){
        response = request.cancelCart(token)
        assertEquals(HttpStatus.SC_OK, response.statusCode())
        assertEquals("Registro excluído com sucesso. Estoque dos produtos reabastecido", response.jsonPath().get("message"))
    }

    @Test
    @Order(2)
    @DisplayName("Criando um carrinho")
    fun `create new cart` (){
        val prod = ProductRequests();
        val resp: Response = prod.getAllProducts()
        val _id = resp.jsonPath().getString("produtos[0]._id")
        response = request.createCart(token,_id, 3)
        assertEquals(HttpStatus.SC_CREATED, response.statusCode())
        assertEquals("Cadastro realizado com sucesso", response.jsonPath().get("message"))

    }

    @Test
    @Order(3)
    @DisplayName("Listando todos carrinhos")
    fun `list all products` (){
        response = request.getCarts()
        assertEquals(HttpStatus.SC_OK, response.statusCode())
    }

    @Test
    @Order(4)
    @DisplayName("Concluindo compra carrinho")
    fun `submit cart` (){
        response = request.submitCart(token)
        assertEquals(HttpStatus.SC_OK, response.statusCode())
        assertEquals("Registro excluído com sucesso", response.jsonPath().get("message"))
    }
}