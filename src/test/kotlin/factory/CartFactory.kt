package factory

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

open class CartFactory {


}
@Serializable
data class Cart(
    @Required var _idProduto:String,
    @Required var quantidade:Int
)

