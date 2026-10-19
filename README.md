# Healthcare Records XML Validation & Transformation System

An enterprise XML data processing, schema validation, transformation, and exchange engine built with **Java 21 / JDK 27**, **W3C XML Schema (XSD 1.0)**, **XPath 1.0/2.0**, **XSLT 1.0**, and **Jakarta XML Binding (JAXB 4.0)**. 

Localized for the **Botswana Healthcare & Medical Aid Ecosystem** (Botswana Pula `BWP`, 9-digit Omang IDs, BHPC practice numbers, and accredited health providers).

---

## 1. Key Features & Competencies Demonstrated

| Feature | Technology | Competency Demonstrated |
| :--- | :--- | :--- |
| **Data Structuring** | XML 1.0 / Namespaces | Well-formed hierarchical XML database modeling plans, providers, members, and claims. |
| **Schema Validation** | W3C XML Schema (XSD) | Strong typing, regex facets (`OmangType`, `PhoneType`), enums, positive decimals, and `xs:key` / `xs:keyref` integrity. |
| **Error Diagnostics** | JAXP `SchemaFactory` / SAX | Captures line numbers, column positions, and violation descriptions; demonstrates error quarantine and resolution. |
| **Targeted Querying** | JAXP `XPath` | Querying complex nodesets, aggregate math functions (`sum()`), filtering by attribute and nested elements. |
| **Data Transformation** | JAXP `Transformer` / XSLT | Transforms raw XML payloads into responsive, styled HTML reports (Member Directory and Claims Audit Statement). |
| **Data Exchange / Binding** | Jakarta XML Binding (JAXB) | Unmarshals XML into Java POJOs and serializes POJOs back into validated XML documents. |

---

## 2. Architecture & File Structure

```
healthcare-xml-system/
├── pom.xml                                       # Maven build file (Java 21, JAXB 4.0, JUnit 5, Shade plugin)
├── README.md                                     # Documentation and technical reference
├── src/
│   ├── main/
│   │   ├── java/com/healthsystem/xml/
│   │   │   ├── Main.java                         # CLI runner with command-line flags
│   │   │   ├── validator/
│   │   │   │   ├── XmlSchemaValidator.java       # JAXP XML/XSD validator with SAX ErrorHandler
│   │   │   │   └── ValidationError.java          # Model capturing line/col and violation details
│   │   │   ├── xpath/
│   │   │   │   ├── HealthcareNamespaceContext.java # XML Namespace resolver for XPath ('hc')
│   │   │   │   └── XPathQueryEngine.java         # Suite of real-world XPath expressions
│   │   │   ├── xslt/
│   │   │   │   └── XsltTransformerService.java   # JAXP Transformer executing XSLT stylesheets
│   │   │   └── jaxb/
│   │   │       ├── HealthcareDatabaseJaxb.java   # JAXB root element binding
│   │   │       ├── PlanJaxb.java                 # JAXB Benefit Plan model
│   │   │       ├── MemberJaxb.java               # JAXB Member model
│   │   │       ├── ClaimJaxb.java                # JAXB Claim model
│   │   │       └── JaxbProcessor.java            # JAXB Unmarshalling and Marshalling engine
│   │   └── resources/
│   │       ├── xsd/
│   │       │   └── healthcare-records.xsd        # Enterprise XSD with types, facets, and keys
│   │       ├── xml/
│   │       │   ├── healthcare-records.xml        # Valid XML database with Botswana healthcare data
│   │       │   ├── invalid-records.xml           # Dirty XML demonstrating 9 deliberate violations
│   │       │   └── corrected-records.xml         # Remediated XML proving 100% compliance
│   │       └── xslt/
│   │           ├── member-directory.xslt         # XSLT -> Responsive HTML Member Directory
│   │           └── claims-audit-report.xslt      # XSLT -> Executive Claims & Financial Statement
│   └── test/
│       └── java/com/healthsystem/xml/
│           ├── XmlSchemaValidatorTest.java       # 3 tests: Valid passes, Invalid caught, Corrected passes
│           ├── XPathQueryEngineTest.java         # 5 tests: Nodesets, sums, string evaluations
│           ├── XsltTransformerTest.java          # 2 tests: Verifies HTML report generation & content
│           └── JaxbProcessorTest.java            # 2 tests: Unmarshal and round-trip marshalling
```

---

## 3. XSD Schema Design (`healthcare-records.xsd`)

The XML Schema enforces strict enterprise constraints:

```xml
<!-- 1. Currency Restriction: Must be Botswana Pula (BWP) -->
<xs:simpleType name="CurrencyType">
    <xs:restriction base="xs:string">
        <xs:enumeration value="BWP"/>
    </xs:restriction>
</xs:simpleType>

<!-- 2. Omang Restriction: Exactly 9 numeric digits -->
<xs:simpleType name="OmangType">
    <xs:restriction base="xs:string">
        <xs:pattern value="[0-9]{9}"/>
    </xs:restriction>
</xs:simpleType>

<!-- 3. Practice Number: BHPC registration format -->
<xs:simpleType name="PracticeNumberType">
    <xs:restriction base="xs:string">
        <xs:pattern value="BHPC-[0-9]{6}"/>
    </xs:restriction>
</xs:simpleType>

<!-- 4. Money Amount: Non-negative with 2 decimal places -->
<xs:simpleType name="MoneyAmountType">
    <xs:restriction base="xs:decimal">
        <xs:minInclusive value="0.00"/>
        <xs:fractionDigits value="2"/>
    </xs:restriction>
</xs:simpleType>
```

### Referential Integrity (`xs:key` & `xs:keyref`)
- `PlanKey` $\rightarrow$ Enforces unique plan codes; `MemberPlanRef` ensures members only enroll in declared plans.
- `ProviderKey` $\rightarrow$ Enforces unique BHPC practice numbers; `ClaimProviderRef` ensures claims reference accredited facilities.
- `MemberKey` $\rightarrow$ Enforces unique membership policy numbers; `ClaimMemberRef` links claims to valid policyholders.

---

## 4. Error Detection & Remediation Workflow

The system validates against dirty inputs (`invalid-records.xml`) to prove automated defect detection:

```
[ERROR] Line 15, Col 41: Value 'USD' is not facet-valid with respect to enumeration '[BWP]'.
[ERROR] Line 41, Col 74: Value 'PENDING_APPROVAL' is not in enumeration '[ACTIVE, SUSPENDED, TERMINATED]'.
[ERROR] Line 42, Col 45: Value '75120' is not facet-valid with respect to pattern '[0-9]{9}' for type 'OmangType'.
[ERROR] Line 43, Col 23: Invalid content found starting with element 'lastName'. One of 'firstName' is expected.
[ERROR] Line 65, Col 64: Value '-850.00' is not facet-valid with respect to minInclusive '0.0' for type 'MoneyAmountType'.
```

Following automated or manual remediation (`corrected-records.xml`), the validation suite confirms:
```
[PASSED] All schema violations successfully resolved in 'corrected-records.xml'!
```

---

## 5. XPath Query Showcase

| Query Objective | XPath Expression |
| :--- | :--- |
| **Executive Plan Members** | `//hc:member[hc:enrolledPlanRef='PLAN-EXECUTIVE']` |
| **High-Value Claims (> BWP 1,000)** | `//hc:claim[number(hc:totalClaimed) > 1000.00]` |
| **Claims from GPH Facility** | `//hc:claim[hc:providerRef='BHPC-001290']` |
| **Cumulative Approved Amount** | `sum(//hc:claim/hc:totalApproved)` |
| **Families with 2+ Dependants** | `//hc:member[count(hc:dependants/hc:dependant) >= 2]` |
| **Audit of Rejected Claims** | `//hc:claim[@status='REJECTED']` |

---

## 6. How to Build & Run

### 1. Run Complete Test Suite (12 automated tests):
```powershell
mvn test
```

### 2. Run the Interactive CLI Pipeline:
```powershell
java -jar target/healthcare-xml-system-1.0.0.jar --all
```

**Specific Execution Modes:**
- `--validate`: Validates production `healthcare-records.xml` against the XSD schema.
- `--diagnose-invalid`: Demonstrates detection of dirty records and remediation validation.
- `--xpath`: Runs and prints results of the XPath query suite.
- `--transform`: Compiles XSLT stylesheets into HTML reports in `target/reports/`.
- `--jaxb`: Demonstrates object unmarshalling and round-trip marshalling.

---

## 7. Generated HTML Reports

Executing `--transform` or `--all` creates:
1. `target/reports/member-directory.html`: Responsive table of policyholders, Omang IDs, contact info, dependant counts, and plan badges.
2. `target/reports/claims-audit-report.html`: Executive financial audit statement comparing claimed vs approved amounts in BWP, copayments, and adjudication findings.
