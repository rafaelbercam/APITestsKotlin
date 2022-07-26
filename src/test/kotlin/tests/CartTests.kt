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

        val prod = ProductRequests();
        lateinit var resp: Response

        resp = prod.getAllProducts()
        // val _id = resp.jsonPath().get<0>("_id")
       // response = request.createCart()
    }
}