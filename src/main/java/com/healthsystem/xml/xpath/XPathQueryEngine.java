package com.healthsystem.xml.xpath;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XPathQueryEngine {

    private final Document document;
    private final XPath xpath;

    public XPathQueryEngine(File xmlFile) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.document = builder.parse(xmlFile);

        XPathFactory xPathFactory = XPathFactory.newInstance();
        this.xpath = xPathFactory.newXPath();
        this.xpath.setNamespaceContext(new HealthcareNamespaceContext());
    }

    public XPathQueryEngine(InputStream xmlStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.document = builder.parse(new InputSource(xmlStream));

        XPathFactory xPathFactory = XPathFactory.newInstance();
        this.xpath = xPathFactory.newXPath();
        this.xpath.setNamespaceContext(new HealthcareNamespaceContext());
    }

    public NodeList queryNodes(String expression) throws XPathExpressionException {
        XPathExpression expr = xpath.compile(expression);
        return (NodeList) expr.evaluate(document, XPathConstants.NODESET);
    }

    public String queryString(String expression) throws XPathExpressionException {
        XPathExpression expr = xpath.compile(expression);
        return (String) expr.evaluate(document, XPathConstants.STRING);
    }

    public Double queryNumber(String expression) throws XPathExpressionException {
        XPathExpression expr = xpath.compile(expression);
        return (Double) expr.evaluate(document, XPathConstants.NUMBER);
    }

    /**
     * Executes a structured showcase of real-world queries demonstrating XPath proficiency.
     */
    public List<String> runDemonstrationSuite() throws XPathExpressionException {
        List<String> results = new ArrayList<>();

        // 1. Members enrolled in PLAN-EXECUTIVE
        NodeList executiveMembers = queryNodes("//hc:member[hc:enrolledPlanRef='PLAN-EXECUTIVE']");
        results.add(String.format("1. Members enrolled in 'PLAN-EXECUTIVE' (Count: %d):", executiveMembers.getLength()));
        for (int i = 0; i < executiveMembers.getLength(); i++) {
            Node m = executiveMembers.item(i);
            String policy = xpath.evaluate("@membershipNumber", m);
            String name = xpath.evaluate("concat(hc:firstName, ' ', hc:lastName)", m);
            String omang = xpath.evaluate("hc:omangNumber", m);
            results.add(String.format("   - Policy: %s | Name: %s | Omang: %s", policy, name, omang));
        }

        // 2. High-value claims exceeding 1,000.00 BWP
        NodeList highClaims = queryNodes("//hc:claim[number(hc:totalClaimed) > 1000.00]");
        results.add(String.format("\n2. High-Value Claims > BWP 1,000.00 (Count: %d):", highClaims.getLength()));
        for (int i = 0; i < highClaims.getLength(); i++) {
            Node c = highClaims.item(i);
            String ref = xpath.evaluate("@claimRef", c);
            String status = xpath.evaluate("@status", c);
            String amount = xpath.evaluate("hc:totalClaimed", c);
            String member = xpath.evaluate("hc:memberRef", c);
            results.add(String.format("   - %s | Member: %s | Amount: BWP %s | Status: %s", ref, member, amount, status));
        }

        // 3. Claims filed at Gaborone Private Hospital (BHPC-001290)
        NodeList gphClaims = queryNodes("//hc:claim[hc:providerRef='BHPC-001290']");
        results.add(String.format("\n3. Claims submitted by Gaborone Private Hospital (BHPC-001290) (Count: %d):", gphClaims.getLength()));
        for (int i = 0; i < gphClaims.getLength(); i++) {
            Node c = gphClaims.item(i);
            String ref = xpath.evaluate("@claimRef", c);
            String serviceDate = xpath.evaluate("hc:serviceDate", c);
            String total = xpath.evaluate("hc:totalClaimed", c);
            results.add(String.format("   - %s | Date: %s | Claimed: BWP %s", ref, serviceDate, total));
        }

        // 4. Sum of all Approved Claims (Aggregated Function)
        Double totalApproved = queryNumber("sum(//hc:claim/hc:totalApproved)");
        results.add(String.format("\n4. Cumulative Scheme Approved Expenditure: BWP %.2f", totalApproved));

        // 5. Members with 2 or more active dependants
        NodeList familyMembers = queryNodes("//hc:member[count(hc:dependants/hc:dependant) >= 2]");
        results.add(String.format("\n5. Principal Members with 2+ Family Dependants (Count: %d):", familyMembers.getLength()));
        for (int i = 0; i < familyMembers.getLength(); i++) {
            Node m = familyMembers.item(i);
            String name = xpath.evaluate("concat(hc:firstName, ' ', hc:lastName)", m);
            String depCount = xpath.evaluate("count(hc:dependants/hc:dependant)", m);
            results.add(String.format("   - %s (Dependants: %s)", name, depCount));
        }

        // 6. Rejected Claims Audit
        NodeList rejectedClaims = queryNodes("//hc:claim[@status='REJECTED']");
        results.add(String.format("\n6. Rejected Claims Audit (Count: %d):", rejectedClaims.getLength()));
        for (int i = 0; i < rejectedClaims.getLength(); i++) {
            Node c = rejectedClaims.item(i);
            String ref = xpath.evaluate("@claimRef", c);
            String reason = xpath.evaluate("hc:rejectionReason", c);
            results.add(String.format("   - %s rejected: \"%s\"", ref, reason));
        }

        return results;
    }
}
