package com.healthsystem.xml;

import com.healthsystem.xml.xslt.XsltTransformerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class XsltTransformerTest {

    private final XsltTransformerService transformer = new XsltTransformerService();

    @Test
    @DisplayName("XSLT Transformation: Transform XML to Member Directory HTML Report")
    void testMemberDirectoryTransformation() throws Exception {
        try (InputStream xml = getClass().getResourceAsStream("/xml/healthcare-records.xml");
             InputStream xslt = getClass().getResourceAsStream("/xslt/member-directory.xslt")) {

            assertNotNull(xml);
            assertNotNull(xslt);

            String html = transformer.transformToString(xml, xslt);
            assertNotNull(html);
            assertTrue(html.contains("<html"), "Output must contain HTML root");
            assertTrue(html.contains("Botswana Medical Aid - Principal Member Directory"));
            assertTrue(html.contains("MED-2024001"), "Output must contain member policy #");
            assertTrue(html.contains("751209700"), "Output must contain Omang number");
            assertTrue(html.contains("Pula Essential Care"), "Output must contain resolved plan name");
        }
    }

    @Test
    @DisplayName("XSLT Transformation: Transform XML to Financial Claims Audit HTML Statement")
    void testClaimsAuditTransformation() throws Exception {
        try (InputStream xml = getClass().getResourceAsStream("/xml/healthcare-records.xml");
             InputStream xslt = getClass().getResourceAsStream("/xslt/claims-audit-report.xslt")) {

            assertNotNull(xml);
            assertNotNull(xslt);

            String html = transformer.transformToString(xml, xslt);
            assertNotNull(html);
            assertTrue(html.contains("Healthcare Medical Claims &amp; Financial Utilization Audit") || html.contains("Healthcare Medical Claims & Financial Utilization Audit"));
            assertTrue(html.contains("CLM-2025001"), "Output must contain claim ref");
            assertTrue(html.contains("BWP"), "Output must format currency in BWP");
            assertTrue(html.contains("Gaborone Private Hospital"), "Output must resolve provider facility name");
        }
    }
}
