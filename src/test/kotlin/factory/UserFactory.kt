package factory

import io.github.serpro69.kfaker.Faker

private val faker = Faker()
val firstName = faker.name.firstName()
val lastName = faker.name.lastName()

data class User (
    var nome: String = "$firstName $lastName",
    var email: String = "${firstName.lowercase()}${lastName.lowercase()}${faker.random.nextInt()}@email.com",
    var password: String = faker.random.nextLong(bound = 9999999L).toString(),
    var administrador: String = "true"
)
