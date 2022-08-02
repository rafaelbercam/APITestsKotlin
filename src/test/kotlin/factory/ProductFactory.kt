package factory

import io.github.serpro69.kfaker.Faker

private val faker = Faker()
val productName = faker.commerce.productName()

data class Product (
    var nome: String = faker.commerce.productName(),
    var preco: Int = faker.random.nextInt(10,999),
    var descricao: String = "Awesome $productName",
    var quantidade: Int = faker.random.nextInt(bound = 9999)
)