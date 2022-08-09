package factory

import io.github.cdimascio.dotenv.dotenv
import io.github.serpro69.kfaker.Faker
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

class UserFactory {

    private var faker = Faker()

    val createUser: User
        get() {
            val firstName = faker.name.firstName()
            val lastName = faker.name.lastName()
            return User(
                nome = "$firstName $lastName",
                email = "${firstName.lowercase()}${lastName.lowercase()}@qa.com",
                password = faker.random.nextLong(bound = 9999999L).toString(),
                administrador = "true"
            )
        }

    val createPreUser: User
        get() {
            val firstName = "Rafael"
            val lastName = "Bercam"
            val dotenv = dotenv()
            return User(
                nome = "$firstName $lastName",
                email = dotenv["EMAIL"],
                password = dotenv["PASSWORD"],
                administrador = "true"
            )
        }
}

@Serializable
data class User(
    @Required var nome: String,
    @Required var email: String,
    @Required var password: String,
    @Required var administrador: String
)
