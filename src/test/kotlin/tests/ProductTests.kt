@file:Suppress("ClassName")
package tests

import core.Setup
import factory.LoginFactory
import factory.ProductFactory
import io.restassured.response.Response
import org.apache.http.HttpStatus
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import requests.LoginRequests
import requests.ProductRequests
import kotlin.test.assertEquals

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ProductTests : Setup() {

    var login = LoginRequests()
    var request = ProductRequests()
    lateinit var response: Response
    lateinit var token: String
    lateinit var _id: String

    @BeforeAll
    fun `get token`() {
        val user = LoginFactory()
        response = login.login(user.loginSucceeded)
        token = response.jsonPath().get("authorization")
    }

    @Test
    @Order(1)
    @DisplayName("Listando todos Produtos")
    fun `list all products`() {
        response = request.getAllProducts()
        assertEquals(HttpStatus.SC_OK, response.statusCode())
    }

    @Test
    @Order(2)
    @DisplayName("Criando novo produto")
    fun `create a new product`() {
        val product = ProductFactory()
        response = request.createNewProduct(product.createProduct, token)
        assertEquals(HttpStatus.SC_CREATED, response.statusCode())
        assertEquals("Cadastro realizado com sucesso", response.jsonPath().get("message"))
    }

    @Test
    @Order(3)
    @DisplayName("consultando produto pelo _id")
    fun `get product by id`() {
        val allProducts: Response = request.getAllProducts()
        _id = allProducts.jsonPath().get("produtos[0]._id")
        response = request.getProductById(_id)
        assertEquals(HttpStatus.SC_OK, response.statusCode())
        assertEquals(_id, response.jsonPath().get("_id"))
    }

    @Test
    @Order(4)
    @DisplayName("alterando um produto")
    fun `update a product`() {
        val product = ProductFactory()
        val allProducts: Response = request.getAllProducts()
        _id = allProducts.jsonPath().get("produtos[0]._id")
        response = request.updateProduct(product.createProduct, _id, token)
        assertEquals(HttpStatus.SC_OK, response.statusCode())
        assertEquals("Registro alterado com sucesso", response.jsonPath().get("message"))
    }

    @Test
    @Order(5)
    @DisplayName("deletando um produto")
    fun `delete a product`() {
        var newProductFac = ProductFactory()
        var newProduct: Response = request.createNewProduct(newProductFac.createProduct, token)
        _id = newProduct.jsonPath().get("_id")
        response = request.deleteProduct(_id, token)
        assertEquals(HttpStatus.SC_OK, response.statusCode())
        assertEquals("Registro excluído com sucesso", response.jsonPath().get("message"))
    }
}
