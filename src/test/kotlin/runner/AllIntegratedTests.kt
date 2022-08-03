package runner

import org.junit.jupiter.api.Disabled
import org.junit.platform.suite.api.SelectClasses
import org.junit.platform.suite.api.SelectPackages
import org.junit.platform.suite.api.Suite
import tests.CartTests
import tests.LoginTests
import tests.ProductTests
import tests.UserTests


@Suite
@Disabled
@SelectClasses(
    LoginTests::class,
    UserTests::class,
    ProductTests::class,
    CartTests::class
)
@SelectPackages("tests")
class AllIntegratedTests
