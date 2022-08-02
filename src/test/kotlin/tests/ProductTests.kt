@file:Suppress("ClassName")
package tests

import core.Setup
import factory.Product
import factory.UserFactory
import io.restassured.response.Response
import org.apache.http.HttpStatus
import org.junit.jupiter.api.*
import requests.LoginRequests
import requests.ProductRequests
import requests.UsersRequests
import kotlin.test.assertEquals

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ProductTests : Setup() {

    var login = LoginRequests()
    var request = ProductRequests()
    lateinit var response: Response
    lateinit var token : String
    lateinit var _id : String
    private var product = Product()
    var usersRequests  = UsersRequests()


    @BeforeAll
    fun `get token` () {
        val user = UserFactory().createUser
        usersRequests.createUser(user)
        response = login.loginRequest(user.email, user.password)
        token = response.jsonPath().get("authorization")
    }

    @Test
    @Order(1)
    @DisplayName("Listando todos Produtos")
    fun `list all products` (){
        response = request.getAllProducts()
        assertEquals(HttpStatus.SC_OK, response.statusCode())
    }

    @Test
    @Order(2)
    @DisplayName("Criando novo produto")
    fun `create a new product` (){
        response = request.createNewProduct(product, token)
        assertEquals(HttpStatus.SC_CREATED, response.statusCode())
        assertEquals("Cadastro realizado com sucesso", response.jsonPath().get("message"))
    }

    @Test
    @Order(3)
    @DisplayName("consultando produto pelo _id")
    fun `get product by id` (){
        val allProducts: Response = request.getAllProducts();
        _id = allProducts.jsonPath().get("produtos[0]._id")
        response = request.getProductById(_id)
        assertEquals(HttpStatus.SC_OK, response.statusCode())
        assertEquals(_id, response.jsonPath().get("_id"))
    }

    @Test
    @Order(4)
    @DisplayName("alterando um produto")
    fun `update a product` (){
        val allProducts: Response = request.getAllProducts();
        _id = allProducts.jsonPath().get("produtos[0]._id")
        product = Product()
        response = request.updateProduct(product,_id,token)
        assertEquals(HttpStatus.SC_OK, response.statusCode())
        assertEquals("Registro alterado com sucesso", response.jsonPath().get("message"))
    }

    @Test
    @Order(5)
    @DisplayName("deletando um produto")
    fun `delete a product` (){
        response = request.deleteProduct(_id, token)
        assertEquals(HttpStatus.SC_OK, response.statusCode())
        assertEquals("Registro excluído com sucesso", response.jsonPath().get("message"))
    }
}