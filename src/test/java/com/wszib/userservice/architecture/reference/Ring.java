package com.wszib.userservice.architecture.reference;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

public enum Ring {
    ADAPTER("adapter", "com.wszib.userservice.adapter.."),
    APPLICATION("application", "com.wszib.userservice.application.."),
    DOMAIN("domain", "com.wszib.userservice.domain.."),
    INFRASTRUCTURE("infrastructure", "com.wszib.userservice.infrastructure.."),
    JPA("jpa adapter", "com.wszib.userservice.adapter.out.persistance.jpa.."),
    JPA_USER("jpa user adapter", "com.wszib.userservice.adapter.out.persistance.jpa.model.."),
    REST("rest adapter", "com.wszib.userservice.adapter.in.web.rest.."),
    REST_USER("rest user adapter", "com.wszib.userservice.adapter.in.web.rest.model..");

    private final String ringName;
    private final String packagePath;

    Ring(String ringName, String packagePath) {
        this.ringName = ringName;
        this.packagePath = packagePath;
    }

    public String ringName() {
        return ringName;
    }

    public DescribedPredicate<JavaClass> packagePath() {
        return JavaClass.Predicates.resideInAPackage(packagePath);
    }
}
