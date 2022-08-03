package factory

data class LoginData(
    var email: String,
    val password: String,
    val wrong_email: String,
    var email_empty: String,
    val password_empty: String
)
