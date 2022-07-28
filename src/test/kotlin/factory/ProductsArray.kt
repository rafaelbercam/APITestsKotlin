package factory

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable
open class ProductsArray {
lateinit var cart: Cart


}
@Serializable
data class ProdutoArray(@Required var produtos: Array<Cart>)