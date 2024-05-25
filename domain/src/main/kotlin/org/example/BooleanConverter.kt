package org.example

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class BooleanConverter : AttributeConverter<Boolean, String> {
    override fun convertToDatabaseColumn(attribute: Boolean): String = if (attribute) "Y" else "N"

    override fun convertToEntityAttribute(dbData: String): Boolean = dbData == "Y"

}