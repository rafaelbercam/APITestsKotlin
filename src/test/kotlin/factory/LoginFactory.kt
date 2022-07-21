package factory

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

open class LoginFactory {

    val loginSucceeded: Login
        get() {
            return Login(
                email = "fulano@qa.com",
                password = "teste"
            )
        }

    val loginFail: Login
        get() {
            return Login(
                email = "fulano@qa.com",
                password = "any"
            )
        }

    val loginEmailRequired: Login
        get() {
            return Login(
                email = "",
                password = "teste"
            )
        }

    val loginPasswordRequired: Login
        get() {
            return Login(
                email = "fulano@qa.com",
                password = ""
            )
        }
}
@Serializable
data class Login (
    @Required var email: String,
    @Required var password: String  )