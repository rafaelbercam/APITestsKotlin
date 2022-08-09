package requests

import core.Setup
import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response

open class CartRequests : Setup() {

    open fun getCarts(): Response {
        val response =
            Given {
                spec(requestSpecification)
            } When {
                get("/carrinhos")
            } Then {
            } Extract {
                response()
            }
        return response
    }

    open fun createCart(token: String, _id: String, qtd: Int): Response {
        val response =
            Given {
                spec(requestSpecification)
                    .header("Authorization", token)
                    .body("{ \"produtos\": [ {\n\"idProduto\": \"$_id\",\n\"quantidade\": $qtd\n}]}")
            } When {
                post("/carrinhos")
            } Then {
            } Extract {
                response()
            }
        return response
    }

    open fun cancelCart(token: String): Response {
        val response =
            Given {
                spec(requestSpecification)
                    .header("Authorization", token)
            } When {
                delete("/carrinhos/cancelar-compra")
            } Then {
            } Extract {
                response()
            }
        return response
    }

    open fun submitCart(token: String): Response {
        val response =
            Given {
                spec(requestSpecification)
                    .header("Authorization", token)
            } When {
                delete("/carrinhos/concluir-compra")
            } Then {
            } Extract {
                response()
            }
        return response
    }
}
