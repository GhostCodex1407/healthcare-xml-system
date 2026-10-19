package com.healthsystem.xml.jaxb;

import jakarta.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "healthcareDatabase", namespace = "http://www.healthsystem.bw/schema/healthcare")
@XmlAccessorType(XmlAccessType.FIELD)
public class HealthcareDatabaseJaxb {

    @XmlAttribute(name = "generatedDate")
    private String generatedDate;

    @XmlAttribute(name = "version")
    private String version;

    @XmlElementWrapper(name = "benefitPlans", namespace = "http://www.healthsystem.bw/schema/healthcare")
    @XmlElement(name = "plan", namespace = "http://www.healthsystem.bw/schema/healthcare")
    private List<PlanJaxb> plans = new ArrayList<>();

    @XmlElementWrapper(name = "members", namespace = "http://www.healthsystem.bw/schema/healthcare")
    @XmlElement(name = "member", namespace = "http://www.healthsystem.bw/schema/healthcare")
    private List<MemberJaxb> members = new ArrayList<>();

    @XmlElementWrapper(name = "claims", namespace = "http://www.healthsystem.bw/schema/healthcare")
    @XmlElement(name = "claim", namespace = "http://www.healthsystem.bw/schema/healthcare")
    private List<ClaimJaxb> claims = new ArrayList<>();

    public String getGeneratedDate() { return generatedDate; }
    public void setGeneratedDate(String generatedDate) { this.generatedDate = generatedDate; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public List<PlanJaxb> getPlans() { return plans; }
    public void setPlans(List<PlanJaxb> plans) { this.plans = plans; }

    public List<MemberJaxb> getMembers() { return members; }
    public void setMembers(List<MemberJaxb> members) { this.members = members; }

    public List<ClaimJaxb> getClaims() { return claims; }
    public void setClaims(List<ClaimJaxb> claims) { this.claims = claims; }
}
