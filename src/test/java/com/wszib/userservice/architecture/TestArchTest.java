package com.wszib.userservice.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.syntax.elements.GivenClassesConjunction;
import com.tngtech.archunit.lang.syntax.elements.GivenMethodsConjunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Configuration;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

@AnalyzeClasses(
        packages = "com.wszib.userservice",
        importOptions = ImportOption.OnlyIncludeTests.class)
@DisplayName("Architecture tests")
class TestArchTest {

    private final GivenClassesConjunction testClasses;
    private final GivenMethodsConjunction testCases;

    TestArchTest(){
        testClasses =
                classes()
                        .that()
                        .resideOutsideOfPackage("com.wszib.userservice.architecture..")
                        .and()
                        .areTopLevelClasses()
                        .and()
                        .areNotAnnotatedWith(Configuration.class)
                        .and()
                        .areNotAnnotatedWith(TestConfiguration.class)
                        .and()
                        .areNotRecords();

        testCases =
                methods()
                        .that()
                        .areDeclaredInClassesThat()
                        .resideOutsideOfPackage("com.wszib.userservice.architecture..")
                        .and()
                        .areDeclaredInClassesThat()
                        .areTopLevelClasses()
                        .and()
                        .areDeclaredInClassesThat()
                        .areNotAnnotatedWith(Configuration.class)
                        .and()
                        .areDeclaredInClassesThat()
                        .areNotAnnotatedWith(TestConfiguration.class)
                        .and()
                        .areDeclaredInClassesThat()
                        .areNotRecords()
                        .and()
                        .arePackagePrivate()
                        .and()
                        .areNotAnnotatedWith(BeforeEach.class);
    }

    @ArchTest
    void shouldNotHavePublicTestClasses(JavaClasses classes) {
        testClasses
                .should().notBePublic()
                .as("Test classes should not be public")
                .because("they don't need to be exposed more than necessary - which is package private scope.")
                .check(classes);
    }

    @ArchTest
    void shouldNotHavePublicTestCases(JavaClasses classes) {
        testCases
                .should().notBePublic()
                .as("Test cases should not be public")
                .because("they don't need to be exposed more than necessary - which is package private scope.")
                .check(classes);
    }

    @ArchTest
    void shouldCorrectNameTestClasses(JavaClasses classes) {
        testClasses
                .should().haveSimpleNameContaining("Test")
                .as("Test classes should have a name containing 'Test'")
                .check(classes);
    }

    @ArchTest
    void shouldDescribeTestClasses(JavaClasses classes) {
        testClasses
                .should().beAnnotatedWith(DisplayName.class)
                .as("Test classes should have a display name")
                .because("it helps to understand the purpose of the test class")
                .check(classes);
    }

    @ArchTest
    void shouldDescribeTestCases(JavaClasses classes) {
        testCases
                .should().beAnnotatedWith(DisplayName.class)
                .as("Test classes should have a display name")
                .because("it helps to understand the purpose of the test class")
                .check(classes);
    }
}
