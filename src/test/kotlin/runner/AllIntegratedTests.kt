package runner

import org.junit.platform.suite.api.SelectClasses
import org.junit.platform.suite.api.SelectPackages
import org.junit.platform.suite.api.Suite
import tests.LoginTests
import tests.ProductTests
import tests.UserTests

@Suite
@SelectClasses(
    LoginTests::class,
    UserTests::class,
    ProductTests::class
)
@SelectPackages("tests")
class AllIntegratedTests {
}