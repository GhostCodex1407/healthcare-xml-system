package com.healthsystem.xml;

import com.healthsystem.xml.jaxb.HealthcareDatabaseJaxb;
import com.healthsystem.xml.jaxb.JaxbProcessor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class JaxbProcessorTest {

    @Test
    @DisplayName("JAXB Unmarshalling: Parse XML Database into Java POJO Hierarchy")
    void testUnmarshalDatabase() throws Exception {
        JaxbProcessor processor = new JaxbProcessor();
        try (InputStream xml = getClass().getResourceAsStream("/xml/healthcare-records.xml")) {
            assertNotNull(xml);
            HealthcareDatabaseJaxb database = processor.unmarshal(xml);

            assertNotNull(database);
            assertEquals("1.0", database.getVersion());
            assertEquals("2026-10-19", database.getGeneratedDate());

            assertNotNull(database.getPlans());
            assertEquals(3, database.getPlans().size(), "Must unmarshal 3 benefit plans");

            assertNotNull(database.getMembers());
            assertTrue(database.getMembers().size() >= 7, "Must unmarshal at least 7 members");

            assertNotNull(database.getClaims());
            assertTrue(database.getClaims().size() >= 6, "Must unmarshal at least 6 claims");
        }
    }

    @Test
    @DisplayName("JAXB Marshalling: Round-trip Serialize POJOs Back to Valid XML")
    void testRoundTripMarshalling() throws Exception {
        JaxbProcessor processor = new JaxbProcessor();
        try (InputStream xml = getClass().getResourceAsStream("/xml/healthcare-records.xml")) {
            HealthcareDatabaseJaxb database = processor.unmarshal(xml);
            String outputXml = processor.marshalToString(database);

            assertNotNull(outputXml);
            assertTrue(outputXml.contains("<healthcareDatabase"), "Serialized XML must contain root tag");
            assertTrue(outputXml.contains("PLAN-ESSENTIAL"), "Serialized XML must contain plan");
            assertTrue(outputXml.contains("MED-2024001"), "Serialized XML must contain member");
        }
    }
}
