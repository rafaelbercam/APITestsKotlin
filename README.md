[![Badge ServeRest](https://img.shields.io/badge/API-ServeRest-green)](https://github.com/ServeRest/ServeRest/)
<a href="https://github.com/rafaelbercam/APITestsKotlin/"><img src="https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg" alt="ktlint"></a>
# __Testes de API em Kotlin__


## __Pré-requisitos__
1. Instalar o [Kotlin](https://kotlinlang.org/docs/command-line.html)

## __Ambiente__
Para executar os testes localmente, estou utilizando o ServeRest

<p align="center">
 <img alt="Logo do ServeRest" src="https://user-images.githubusercontent.com/29241659/115161869-6a017e80-a076-11eb-9bbe-c391eff410db.png" height="120">
</p>

Link do Repo: https://github.com/ServeRest/ServeRest

ServeRest está disponível de forma [online](https://serverest.dev), no [npm](https://www.npmjs.com/package/serverest) e no [docker](https://hub.docker.com/r/paulogoncalvesbh/serverest/).

## __Instalando Dependências__

###Instalar via [Gradle](https://gradle.org/)



## __Configuração do Projeto__
### Estrutura de Pastas
O projeto esta dividido da seguinte maneira:

    [APITestsKotlin]
       [src] -> código fonte
            [core] -> setup do requestspecification do REST-assured
            [factory] -> Properties que retornam objetos criados através de uma sererialização
            [requests] -> Métodos que retornam o objeto Response do REST-assured
            [runner] -> Runner do JUnit
            [tests] -> Arquivos de teste do JUnit


### core

A classe `Setup` é responsável por configurar o `RequestSpecification` do REST-assured antes de realizar as requisições,
e para isso usamos a notação ``BeforeAll`` do JUnit para antes dos testes rodarem. Isso é possível 
graças a anotação `@TestInstance` que nesse caso estamos usando a `LifeCycle.PER_CLASS` que permite configurar o cliclo de teste
do JUnit através desta classe.

E temos também um `tearDown` que aciona o reset do REST-assured.

```kotlin
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class Setup {

    companion object {
        lateinit var requestSpecification: RequestSpecification
    }

    @BeforeAll
    fun setup(){
        val logConfig = LogConfig.logConfig()
            .enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL)
        val config = RestAssuredConfig.config().logConfig(logConfig)

        requestSpecification = RequestSpecBuilder()
            .setBaseUri("http://localhost:3000")
            .setContentType(ContentType.JSON)
            .setRelaxedHTTPSValidation()
            .setConfig(config)
            .build()
    }

    @AfterAll
    fun tearDown(){
        RestAssured.reset()
    }
}
```

### factory

Aqui temos classes capazes de abstrair objetos que serão enviados para as requisições.
Para isso foi utilizado o `kotlinx.serialization.Serializable`.

Essa biblioteca possui processos que são capazes de fazer uma serealização de um objeto para formatos
de uso comum entre as aplicações como o `JSON`.

Então nessas classes foi utilizado Properties que retornam objetos definidos por uma `data class` no formato 
definido pela anotação `@Serializable`

```kotlin
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
    
    //other methods
}
@Serializable
data class Login (
    @Required var email: String,
    @Required var password: String  )
```

### requests

Aqui estão as classes responsáveis por realizar as requisições dos testes,
retornando o objeto `Response` do REST-assured onde será capaz de realizar uma desserialização
do objeto e realizar as asserções dos testes.

Para isso acontecer no Kotlin, foi utilizado a dependência `io.restassured.module.kotlin.extensions` para usar 
os verbos `Given, When, Then, Extract` do REST-assured.

```kotlin
open class LoginRequests : Setup() {

    open fun login (login: Login) : Response {

        val response =
            Given {
                spec(requestSpecification)
                    .filter(RequestLoggingFilter(LogDetail.ALL))
                    .filter(ResponseLoggingFilter(LogDetail.ALL))
                    .body(Json.encodeToString(login))
            } When {
                post("/login")
            } Then {

            } Extract {
                response()
            }
        return response
    }
}
```
Repare que extendemos a class `Setup` para essas classes de reqquisições, para ser capaz
de user o `requestSpecification` que contem a preparação dos nossos testes.

Outro ponto interessante é que passamos por parâmetro exatamente o `data class` que define o modelo do objeto 
esperado. 

### runner

Essa classe serve para controlar quais são as classes ou pacotes que pertencem ao grupo de testes a serem executados.
Esses grupos chamamos de `@Suite` pelo JUnit

```kotlin
@Suite
@SelectClasses(
    LoginTests::class,
    UserTests::class,
    ProductTests::class,
    CartTests::class
)
@SelectPackages("tests")
class AllIntegratedTests {
}
```

O exemplo esta redundante, mas foi para exemplificar que podemos fazer da seguinte forma:

- `@SelectClasses`: onde defino quais classes de teste serão executadas por este runner
- `@SelectPackages`: onde defino quais pacotes serão executados.

Podem ser criados outros runners dependendo do tipo de teste, como por exemplo
testes de regressão e testes de uma feature específica.

### tests

E por ultimo, aqui estão as classes de teste do JUnit

```kotlin
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class LoginTests: Setup() {

    var request = LoginRequests()
    lateinit var response: Response
    var login = LoginFactory()

    @Test
    @Order(1)
    @DisplayName("Login bem sucedido")
    fun `login succeeded`() {
        response = request.login(login.loginSucceeded)
        assertEquals(HttpStatus.SC_OK, response.statusCode)
        assertEquals("Login realizado com sucesso", response.jsonPath().get("message"))
    }

    @Test
    @Order(2)
    @DisplayName("Login com falha")
    fun `login failed`() {
        response = request.login(login.loginFail)
        assertEquals(HttpStatus.SC_UNAUTHORIZED, response.statusCode)
        assertEquals("Email e/ou senha inválidos", response.jsonPath().get("message"))
    }
}
```

Para seguir uma sequencia de execução, o JUnit 5 possui uma anotação `@TestMethodOrder`
que tem capacidade de decidir qual será a ordem de execução dos meus testes.
No exemplo utilizei o `OrderAnnotation::class` que é representado pelo anotação `@Order`.
Essa anotação espera um número inteiro na qual seguirá uma ordem crescente de execução.

OBS: Não utilize para criar uma dependência de um cenário para outro, mas sim organizar os reports, analisar os logs, etc.

### Allure

Para gerar os reports basta rodar os commandos

`./gradlew allureReport` e `./gradlew allureServe`