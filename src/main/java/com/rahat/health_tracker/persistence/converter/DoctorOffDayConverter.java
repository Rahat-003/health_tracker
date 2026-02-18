package com.rahat.health_tracker.persistence.converter;

import com.rahat.health_tracker.enums.DoctorOffDay;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

@Converter
public class DoctorOffDayConverter implements AttributeConverter<EnumSet<DoctorOffDay>, String> {

    private static final String SEPARATOR = ", ";

    @Override
    public String convertToDatabaseColumn(EnumSet<DoctorOffDay> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null; // DB column remains NULL
        }
        return attribute.stream()
                .map(Enum::name)
                .collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public EnumSet<DoctorOffDay> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return EnumSet.noneOf(DoctorOffDay.class);
        }

        return Arrays.stream(dbData.split(SEPARATOR))
                .map(String::trim)
                .map(DoctorOffDay::valueOf)
                .collect(Collectors.toCollection(() ->
                        EnumSet.noneOf(DoctorOffDay.class)));
    }
}
