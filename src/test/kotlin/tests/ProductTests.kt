@file:Suppress("ClassName")
package tests

import core.Setup
import factory.Product
import factory.User
import io.restassured.response.Response
import org.apache.http.HttpStatus
import org.junit.jupiter.api.*
import requests.LoginRequests
import requests.ProductRequests
import requests.UsersRequests
import kotlin.test.assertEquals

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ProductTests : Setup() {

    private var login = LoginRequests()
    private var productRequest = ProductRequests()
    private lateinit var response: Response
    private lateinit var token : String
    private lateinit var _id : String
    private var usersRequests  = UsersRequests()
    private val user = User()

    @BeforeAll
    fun `get token` () {
        usersRequests.createUser(user)
        response = login.loginRequest(user.email, user.password)
        token = response.jsonPath().get("authorization")
    }

    @Test
    @Order(1)
    @DisplayName("Listando todos Produtos")
    fun `list all products` (){
        response = productRequest.getAllProducts()
        assertEquals(HttpStatus.SC_OK, response.statusCode())
    }

    @Test
    @Order(2)
    @DisplayName("Criando novo produto")
    fun `create a new product` (){
        response = productRequest.createNewProduct(Product(), token)
        _id = response.jsonPath().get("_id")
        assertEquals(HttpStatus.SC_CREATED, response.statusCode())
        assertEquals("Cadastro realizado com sucesso", response.jsonPath().get("message"))
    }

    @Test
    @Order(3)
    @DisplayName("consultando produto pelo _id")
    fun `get product by id` (){
        response = productRequest.getProductById(_id)
        assertEquals(HttpStatus.SC_OK, response.statusCode())
        assertEquals(_id, response.jsonPath().get("_id"))
    }

    @Test
    @Order(4)
    @DisplayName("alterando um produto")
    fun `update a product` (){
        response = productRequest.updateProduct(Product(),_id,token)
        assertEquals(HttpStatus.SC_OK, response.statusCode())
        assertEquals("Registro alterado com sucesso", response.jsonPath().get("message"))
    }

    @Test
    @Order(5)
    @DisplayName("deletando um produto")
    fun `delete a product` (){
        val response = productRequest.deleteProduct(_id, token)
        assertEquals(HttpStatus.SC_OK, response.statusCode())
        assertEquals("Registro excluído com sucesso", response.jsonPath().get("message"))
    }
}