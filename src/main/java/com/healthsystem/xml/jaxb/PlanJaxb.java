package com.healthsystem.xml.jaxb;

import jakarta.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlanJaxb {

    @XmlAttribute(name = "planCode")
    private String planCode;

    @XmlElement(name = "planName", namespace = "http://www.healthsystem.bw/schema/healthcare")
    private String planName;

    @XmlElement(name = "annualLimit", namespace = "http://www.healthsystem.bw/schema/healthcare")
    private BigDecimal annualLimit;

    @XmlElement(name = "drugLimit", namespace = "http://www.healthsystem.bw/schema/healthcare")
    private BigDecimal drugLimit;

    @XmlElement(name = "consultationCopayPct", namespace = "http://www.healthsystem.bw/schema/healthcare")
    private BigDecimal consultationCopayPct;

    public String getPlanCode() { return planCode; }
    public void setPlanCode(String planCode) { this.planCode = planCode; }

    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }

    public BigDecimal getAnnualLimit() { return annualLimit; }
    public void setAnnualLimit(BigDecimal annualLimit) { this.annualLimit = annualLimit; }

    public BigDecimal getDrugLimit() { return drugLimit; }
    public void setDrugLimit(BigDecimal drugLimit) { this.drugLimit = drugLimit; }

    public BigDecimal getConsultationCopayPct() { return consultationCopayPct; }
    public void setConsultationCopayPct(BigDecimal consultationCopayPct) { this.consultationCopayPct = consultationCopayPct; }
}
