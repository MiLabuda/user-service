package com.wszib.userservice.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.DisplayName;

import static com.wszib.userservice.architecture.reference.Ring.*;

@AnalyzeClasses(
        packages = "com.wszib.userservice",
        importOptions = {
            ImportOption.DoNotIncludeTests.class,
            ImportOption.DoNotIncludeJars.class})
@DisplayName("Package structure tests")
class PackageStructureArchTest {

    private static final String SPRING_BOOT_APPLICATION_CLASS_NAME = "UserServiceApplication";
    private static final String USER_SERVICE = "User Service";

    PackageStructureArchTest() {
    }

    @ArchTest
    void shouldRespectOnionArchitecture(JavaClasses classes) {
        onion()
                .whereLayer(ADAPTER.ringName()).mayOnlyBeAccessedByLayers(USER_SERVICE)
                .whereLayer(APPLICATION.ringName()).mayOnlyBeAccessedByLayers(ADAPTER.ringName(), USER_SERVICE)
                .whereLayer(DOMAIN.ringName()).mayOnlyBeAccessedByLayers(ADAPTER.ringName(),APPLICATION.ringName())
                .whereLayer(INFRASTRUCTURE.ringName()).mayOnlyBeAccessedByLayers(ADAPTER.ringName(), APPLICATION.ringName(), DOMAIN.ringName())
                .check(classes);
    }

    @ArchTest
    void shouldHaveAdapterPackagesSeparated(JavaClasses classes) {
        adapters()
                .whereLayer(JPA_USER.ringName()).mayOnlyBeAccessedByLayers(JPA.ringName())
                .whereLayer(REST_USER.ringName()).mayOnlyBeAccessedByLayers(REST.ringName())
                .check(classes);
    }

    private Architectures.LayeredArchitecture adapters() {
        return Architectures.layeredArchitecture().consideringAllDependencies()
                .layer(APPLICATION.ringName()).definedBy(APPLICATION.packagePath())
                .layer(JPA.ringName()).definedBy(JPA.packagePath())
                .layer(JPA_USER.ringName()).definedBy(JPA_USER.packagePath())
                .layer(REST.ringName()).definedBy(REST.packagePath())
                .layer(REST_USER.ringName()).definedBy(REST_USER.packagePath())
                .layer(USER_SERVICE).definedBy(JavaClass.Predicates.simpleName(SPRING_BOOT_APPLICATION_CLASS_NAME));
    }

    private Architectures.LayeredArchitecture onion() {
        return Architectures.layeredArchitecture().consideringAllDependencies()
                .layer(ADAPTER.ringName()).definedBy(ADAPTER.packagePath())
                .layer(APPLICATION.ringName()).definedBy(APPLICATION.packagePath())
                .layer(DOMAIN.ringName()).definedBy(DOMAIN.packagePath())
                .layer(INFRASTRUCTURE.ringName()).definedBy(INFRASTRUCTURE.packagePath())
                .layer(USER_SERVICE).definedBy(JavaClass.Predicates.simpleName(SPRING_BOOT_APPLICATION_CLASS_NAME));

    }
}
