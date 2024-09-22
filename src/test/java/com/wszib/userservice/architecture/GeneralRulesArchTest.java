package com.wszib.userservice.architecture;


import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import org.junit.jupiter.api.DisplayName;
import org.springframework.stereotype.Service;

import java.util.function.*;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.wszib.userservice")
@DisplayName("General architecture rules")
class GeneralRulesArchTest {

    @ArchTest
    void shouldLimitImplementationsOfFunctionalInterfacesToPackageScopeAtMost(
            final JavaClasses classes) {
        classes()
                .that().areAssignableTo(Function.class)
                .or().areAssignableTo(Predicate.class)
                .or().areAssignableTo(Supplier.class)
                .or().areAssignableTo(Consumer.class)
                .should().notBePublic()
                .as("Functional interface implementations should not be public")
                .because("they are not meant to be used outside of the package")
                .check(classes);
    }

    @ArchTest
    void shouldUseFunctionsAsMappers(final JavaClasses classes) {
        classes()
                .that().haveSimpleNameEndingWith("Mapper")
                .should().beAssignableTo(Function.class)
                .orShould().beAssignableTo(BiFunction.class)
                .as("Mapper classes should be implementation of Functional interfaces")
                .because("they are transforming information from one type to another")
                .check(classes);
    }

    @ArchTest
    void shouldNotUseMapstruct(final JavaClasses classes) {
        noClasses()
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("org.mapstruct")
                .as("Mapper classes should be functional interface implementation")
                .because("mapstruct is not allowed in this project")
                .check(classes);
    }

    @ArchTest
    void shouldNotDependServiceOnService(final JavaClasses classes) {
        noClasses()
                .that()
                .areAnnotatedWith(Service.class)
                .or()
                .resideInAnyPackage("com.wszib.userservice.application.service")
                .and()
                .haveSimpleNameNotEndingWith("Test")
                .should()
                .dependOnClassesThat()
                .areAnnotatedWith(Service.class)
                .as("Service classes should not depend on other service classes")
                .check(classes);
    }
}
