package com.cropdata.validater;

import java.util.Arrays;

import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Component
public class OrderEnumValidator {

    public static <E extends Enum<E>> E validateEnum(Class<E> enumClass, String value) {
        try {
            return Enum.valueOf(enumClass, value);
        } catch (IllegalArgumentException ex) {
            String validValues = Arrays.toString(enumClass.getEnumConstants());
            throw new IllegalArgumentException("Invalid value for enum " + enumClass.getSimpleName() +
                    ". Valid values are: " + validValues);
        }
    }
}
