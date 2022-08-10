package runner

import org.junit.platform.suite.api.SelectClasses
import org.junit.platform.suite.api.Suite
import tests.CartTests
import tests.LoginTests
import tests.ProductTests
import tests.UserTests

@Suite
@SelectClasses(
    LoginTests::class,
    UserTests::class,
    ProductTests::class,
    CartTests::class
)
class AllIntegratedTests
