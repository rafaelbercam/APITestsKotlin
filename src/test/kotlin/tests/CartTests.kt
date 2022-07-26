@file:Suppress("ClassName")
package tests

import core.Setup
import factory.LoginFactory
import io.restassured.response.Response
import org.apache.http.HttpStatus
import org.junit.jupiter.api.*
import requests.CartRequests
import requests.LoginRequests
import requests.ProductRequests
import kotlin.test.assertEquals
import kotlin.test.expect

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class CartTests: Setup() {

    var login = LoginRequests()
    var request = CartRequests()
    lateinit var response: Response
    var token : String = ""

    @BeforeEach
    fun `get token` () {
        val user = LoginFactory()
        response = login.login(user.loginSucceeded)
        token = response.jsonPath().get("authorization")
    }

    @Test
    @Order(1)
    @DisplayName("Listando todos carrinhos")
    fun `list all products` (){
        response = request.getCarts()
        assertEquals(HttpStatus.SC_OK, response.statusCode())
    }

    @Test
    @Order(2)
    @DisplayName("Criando um carrinho")
    fun `create new cart` (){

//        val prod = ProductRequests();
//        val resp: Response = prod.getAllProducts()
//        val _id = resp.jsonPath().getString("produtos[0]._id")
//        response = request.createCart(_id, 3)
//        assertEquals(HttpStatus.SC_CREATED, response.statusCode())
//        assertEquals("Cadastro realizado com sucesso", response.jsonPath().get("message"))
    }
}