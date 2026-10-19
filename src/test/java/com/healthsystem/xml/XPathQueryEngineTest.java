package com.healthsystem.xml;

import com.healthsystem.xml.xpath.XPathQueryEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XPathQueryEngineTest {

    private XPathQueryEngine queryEngine;

    @BeforeEach
    void setUp() throws Exception {
        try (InputStream xmlStream = getClass().getResourceAsStream("/xml/healthcare-records.xml")) {
            assertNotNull(xmlStream);
            this.queryEngine = new XPathQueryEngine(xmlStream);
        }
    }

    @Test
    @DisplayName("XPath Query: Find Members Enrolled in PLAN-EXECUTIVE")
    void testQueryExecutiveMembers() throws Exception {
        NodeList nodes = queryEngine.queryNodes("//hc:member[hc:enrolledPlanRef='PLAN-EXECUTIVE']");
        assertNotNull(nodes);
        assertEquals(2, nodes.getLength(), "Should find exactly 2 members enrolled in PLAN-EXECUTIVE");
    }

    @Test
    @DisplayName("XPath Query: Find Claims Greater than BWP 1,000.00")
    void testQueryHighValueClaims() throws Exception {
        NodeList nodes = queryEngine.queryNodes("//hc:claim[number(hc:totalClaimed) > 1000.00]");
        assertNotNull(nodes);
        assertTrue(nodes.getLength() >= 3, "Should locate high-value claims > 1000 BWP");
    }

    @Test
    @DisplayName("XPath Query: Cumulative Sum of Scheme Approved Benefits")
    void testSumApprovedExpenditure() throws Exception {
        Double sum = queryEngine.queryNumber("sum(//hc:claim/hc:totalApproved)");
        assertNotNull(sum);
        assertTrue(sum > 5000.00, "Cumulative approved claims expenditure should exceed 5,000 BWP");
    }

    @Test
    @DisplayName("XPath Query: Retrieve Rejected Claims & Audit Findings")
    void testQueryRejectedClaims() throws Exception {
        NodeList rejected = queryEngine.queryNodes("//hc:claim[@status='REJECTED']");
        assertEquals(1, rejected.getLength(), "Should locate 1 rejected claim");

        String reason = queryEngine.queryString("//hc:claim[@status='REJECTED']/hc:rejectionReason");
        assertNotNull(reason);
        assertTrue(reason.contains("SUSPENDED"), "Rejection reason must detail suspension finding");
    }

    @Test
    @DisplayName("XPath Demonstration Suite Executes Cleanly")
    void testDemonstrationSuite() throws Exception {
        List<String> output = queryEngine.runDemonstrationSuite();
        assertNotNull(output);
        assertFalse(output.isEmpty());
    }
}
