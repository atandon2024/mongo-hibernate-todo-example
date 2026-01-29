package org.example;

import org.hibernate.type.descriptor.java.StringJavaType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.hibernate.type.BasicType;
import org.hibernate.type.descriptor.WrapperOptions;

public class UpperCaseStringType extends org.hibernate.type.AbstractSingleColumnStandardBasicType<String> {

    public UpperCaseStringType() {
        super(VarcharJdbcType.INSTANCE, new UpperCaseStringJavaType());
    }

    @Override
    public String getName() {
        return "uppercase-string";
    }

    public static class UpperCaseStringJavaType extends StringJavaType {
        @Override
        public String toString(String value) {
            return value != null ? value.toUpperCase() : null;
        }

        @Override
        public String fromString(CharSequence sequence) {
            return sequence != null ? sequence.toString().toUpperCase() : null;
        }
    }
}