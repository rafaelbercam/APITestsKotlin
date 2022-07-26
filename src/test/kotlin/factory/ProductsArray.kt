package factory

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable
open class ProductsArray {

}
@Serializable
data class ProdutoArray(@Required var produtos: Array<Cart>)