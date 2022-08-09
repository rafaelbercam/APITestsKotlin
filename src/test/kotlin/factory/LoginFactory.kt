package factory

import io.github.cdimascio.dotenv.dotenv
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

open class LoginFactory {

    val dotenv = dotenv()

    val loginSucceeded: Login
        get() {
            return Login(
                email = dotenv["EMAIL"],
                password = dotenv["PASSWORD"]
            )
        }

    val loginFail: Login
        get() {
            return Login(
                email = dotenv["EMAIL"],
                password = "any"
            )
        }

    val loginEmailRequired: Login
        get() {
            return Login(
                email = "",
                password = dotenv["PASSWORD"]
            )
        }

    val loginPasswordRequired: Login
        get() {
            return Login(
                email = dotenv["EMAIL"],
                password = ""
            )
        }
}

@Serializable
data class Login(
    @Required var email: String,
    @Required var password: String
)
