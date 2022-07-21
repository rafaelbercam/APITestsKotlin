package factory

import io.github.serpro69.kfaker.Faker
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

class ProductFactory {
    var faker = Faker()
    val createProduct: Product
        get() {
            val productName = faker.commerce.productName()
            return Product(
                nome = productName,
                preco = faker.random.nextInt(bound = 999),
                descricao = "Awesome $productName",
                quantidade = faker.random.nextInt(bound = 9999)
            )
        }
}

@Serializable
data class Product (
    @Required var nome: String,
    @Required var preco: Int,
    @Required var descricao: String,
    @Required var quantidade: Int )