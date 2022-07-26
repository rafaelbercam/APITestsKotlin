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

open class CartRequests: Setup() {

    open fun getCarts (): Response {
        val response =
            Given {
                spec(requestSpecification)
                    .filter(RequestLoggingFilter(LogDetail.ALL))
                    .filter(ResponseLoggingFilter(LogDetail.ALL))
            } When {
                get("/carrinhos")
            } Then {

            } Extract {
                response()
            }
        return response
    }

    open fun createCart(_id: String, qtd:Int): Response {
        val cart = arrayOf("{$_id: $qtd}")
        val response =
            Given {
                spec(requestSpecification)
                    .filter(RequestLoggingFilter(LogDetail.ALL))
                    .filter(ResponseLoggingFilter(LogDetail.ALL))
                    .body(cart)
            } When {
                post("/carrinhos")
            } Then {

            } Extract {
                response()
            }
        return response
    }
}