package com.healthsystem.xml;

import com.healthsystem.xml.validator.ValidationError;
import com.healthsystem.xml.validator.XmlSchemaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XmlSchemaValidatorTest {

    private XmlSchemaValidator validator;

    @BeforeEach
    void setUp() throws Exception {
        try (InputStream xsdStream = getClass().getResourceAsStream("/xsd/healthcare-records.xsd")) {
            assertNotNull(xsdStream, "XSD schema file must exist in classpath");
            this.validator = new XmlSchemaValidator(xsdStream);
        }
    }

    @Test
    @DisplayName("Valid XML Database Conforms 100% to XSD Schema")
    void testValidXmlPassesValidation() throws Exception {
        try (InputStream xmlStream = getClass().getResourceAsStream("/xml/healthcare-records.xml")) {
            assertNotNull(xmlStream, "healthcare-records.xml must exist");
            List<ValidationError> errors = validator.validate(xmlStream);
            assertTrue(errors.isEmpty(), "Valid XML must have 0 validation errors, but got: " + errors);
        }
    }

    @Test
    @DisplayName("Invalid XML Detects Injected Schema Violations (Omang, Enum, Negative Values, Missing Elements)")
    void testInvalidXmlCatchesExpectedViolations() throws Exception {
        try (InputStream invalidStream = getClass().getResourceAsStream("/xml/invalid-records.xml")) {
            assertNotNull(invalidStream, "invalid-records.xml must exist");
            List<ValidationError> errors = validator.validate(invalidStream);

            assertFalse(errors.isEmpty(), "Invalid XML must trigger validation errors");
            assertTrue(errors.size() >= 4, "Expected at least 4 schema violations, found: " + errors.size());

            // Check that errors contain line numbers
            for (ValidationError err : errors) {
                assertTrue(err.getLineNumber() > 0, "Error must capture line number");
                assertNotNull(err.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Corrected XML Successfully Resolves All Injected Schema Errors")
    void testCorrectedXmlPassesValidation() throws Exception {
        try (InputStream correctedStream = getClass().getResourceAsStream("/xml/corrected-records.xml")) {
            assertNotNull(correctedStream, "corrected-records.xml must exist");
            List<ValidationError> errors = validator.validate(correctedStream);
            assertTrue(errors.isEmpty(), "Corrected XML must pass with 0 errors, but got: " + errors);
        }
    }
}
