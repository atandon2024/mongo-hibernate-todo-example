package org.example;

import jakarta.persistence.PrePersist;
import org.hibernate.Interceptor;

import java.io.Serializable;
import java.lang.reflect.Type;

public class AuditInterceptor implements Interceptor {

    @PrePersist
    public boolean onPersist(
            Object entity, Serializable id,
            Object[] state, String[] propertyNames, Type[] types
    ) {
        System.out.println("Saving entity: " + entity.toString());
        return true;
    }
}