package com.healthsystem.xml;

import com.healthsystem.xml.jaxb.HealthcareDatabaseJaxb;
import com.healthsystem.xml.jaxb.JaxbProcessor;
import com.healthsystem.xml.validator.ValidationError;
import com.healthsystem.xml.validator.XmlSchemaValidator;
import com.healthsystem.xml.xpath.XPathQueryEngine;
import com.healthsystem.xml.xslt.XsltTransformerService;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class Main {

    private static final String XSD_PATH = "/xsd/healthcare-records.xsd";
    private static final String VALID_XML_PATH = "/xml/healthcare-records.xml";
    private static final String INVALID_XML_PATH = "/xml/invalid-records.xml";
    private static final String CORRECTED_XML_PATH = "/xml/corrected-records.xml";
    private static final String MEMBER_XSLT_PATH = "/xslt/member-directory.xslt";
    private static final String CLAIMS_XSLT_PATH = "/xslt/claims-audit-report.xslt";

    public static void main(String[] args) {
        String mode = (args.length > 0) ? args[0].toLowerCase() : "--all";

        System.out.println("================================================================================");
        System.out.println("  Botswana Healthcare XML Data Validation & Transformation Engine (JAXP/XSLT)  ");
        System.out.println("================================================================================");

        try {
            switch (mode) {
                case "--validate":
                    runValidation();
                    break;
                case "--diagnose-invalid":
                    runInvalidDiagnostics();
                    break;
                case "--xpath":
                    runXPathDemonstration();
                    break;
                case "--transform":
                    runXsltTransformation();
                    break;
                case "--jaxb":
                    runJaxbProcessing();
                    break;
                case "--all":
                default:
                    runValidation();
                    System.out.println();
                    runInvalidDiagnostics();
                    System.out.println();
                    runXPathDemonstration();
                    System.out.println();
                    runXsltTransformation();
                    System.out.println();
                    runJaxbProcessing();
                    break;
            }

            System.out.println("\n[SUCCESS] XML Processing Pipeline completed successfully.");
        } catch (Exception e) {
            System.err.println("\n[FATAL ERROR] XML Pipeline execution failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void runValidation() throws Exception {
        System.out.println(">>> [1/5] Validating Production XML Database Against XSD Schema...");
        try (InputStream xsdStream = Main.class.getResourceAsStream(XSD_PATH);
             InputStream xmlStream = Main.class.getResourceAsStream(VALID_XML_PATH)) {

            XmlSchemaValidator validator = new XmlSchemaValidator(xsdStream);
            List<ValidationError> errors = validator.validate(xmlStream);

            if (errors.isEmpty()) {
                System.out.println("    [PASSED] 'healthcare-records.xml' conforms 100% to 'healthcare-records.xsd'!");
            } else {
                System.err.println("    [FAILED] Validation errors found (" + errors.size() + "):");
                errors.forEach(err -> System.err.println("    " + err));
            }
        }
    }

    public static void runInvalidDiagnostics() throws Exception {
        System.out.println(">>> [2/5] Demonstrating Error Detection & Quarantine Workflow...");

        // Step 1: Validate dirty file and display exact errors
        try (InputStream xsdStream = Main.class.getResourceAsStream(XSD_PATH);
             InputStream invalidXmlStream = Main.class.getResourceAsStream(INVALID_XML_PATH)) {

            XmlSchemaValidator validator = new XmlSchemaValidator(xsdStream);
            List<ValidationError> errors = validator.validate(invalidXmlStream);

            System.out.println("    Expected errors detected in 'invalid-records.xml' (" + errors.size() + " violations found):");
            for (int i = 0; i < errors.size(); i++) {
                System.out.println("    [" + (i + 1) + "] " + errors.get(i));
            }
        }

        // Step 2: Validate corrected file proving resolution
        System.out.println("    Validating 'corrected-records.xml' following remediation...");
        try (InputStream xsdStream = Main.class.getResourceAsStream(XSD_PATH);
             InputStream correctedXmlStream = Main.class.getResourceAsStream(CORRECTED_XML_PATH)) {

            XmlSchemaValidator validator = new XmlSchemaValidator(xsdStream);
            List<ValidationError> errors = validator.validate(correctedXmlStream);

            if (errors.isEmpty()) {
                System.out.println("    [PASSED] All schema violations successfully resolved in 'corrected-records.xml'!");
            } else {
                System.err.println("    [FAILED] Unresolved errors: " + errors.size());
            }
        }
    }

    public static void runXPathDemonstration() throws Exception {
        System.out.println(">>> [3/5] Executing Real-Time XPath Query Suite...");
        try (InputStream xmlStream = Main.class.getResourceAsStream(VALID_XML_PATH)) {
            XPathQueryEngine engine = new XPathQueryEngine(xmlStream);
            List<String> results = engine.runDemonstrationSuite();
            results.forEach(r -> System.out.println("    " + r));
        }
    }

    public static void runXsltTransformation() throws Exception {
        System.out.println(">>> [4/5] Executing XSLT Stylesheet Transformations into HTML Reports...");
        XsltTransformerService transformer = new XsltTransformerService();

        File outputDir = new File("target/reports");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        // 1. Member Directory Report
        File memberReport = new File(outputDir, "member-directory.html");
        try (InputStream xml = Main.class.getResourceAsStream(VALID_XML_PATH);
             InputStream xslt = Main.class.getResourceAsStream(MEMBER_XSLT_PATH)) {
            String html = transformer.transformToString(xml, xslt);
            java.nio.file.Files.writeString(memberReport.toPath(), html);
            System.out.println("    [GENERATED] Member Policy Directory -> " + memberReport.getAbsolutePath());
        }

        // 2. Claims Audit Statement Report
        File claimsReport = new File(outputDir, "claims-audit-report.html");
        try (InputStream xml = Main.class.getResourceAsStream(VALID_XML_PATH);
             InputStream xslt = Main.class.getResourceAsStream(CLAIMS_XSLT_PATH)) {
            String html = transformer.transformToString(xml, xslt);
            java.nio.file.Files.writeString(claimsReport.toPath(), html);
            System.out.println("    [GENERATED] Claims & Financial Audit Statement -> " + claimsReport.getAbsolutePath());
        }
    }

    public static void runJaxbProcessing() throws Exception {
        System.out.println(">>> [5/5] Demonstrating JAXB Object Mapping & XML Data Exchange...");
        JaxbProcessor processor = new JaxbProcessor();

        try (InputStream xml = Main.class.getResourceAsStream(VALID_XML_PATH)) {
            HealthcareDatabaseJaxb db = processor.unmarshal(xml);
            System.out.println("    [UNMARSHALLED] Schema Version: " + db.getVersion() + " (Date: " + db.getGeneratedDate() + ")");
            System.out.println("    - Benefit Plans Loaded: " + db.getPlans().size());
            System.out.println("    - Principal Members Loaded: " + db.getMembers().size());
            System.out.println("    - Claims Loaded: " + db.getClaims().size());

            // Demonstrate clean round-trip marshalling
            String marshalledXml = processor.marshalToString(db);
            System.out.println("    [MARSHALLED] Java Objects re-marshalled to XML payload (" + marshalledXml.length() + " bytes).");
        }
    }
}
