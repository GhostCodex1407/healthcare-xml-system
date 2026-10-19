package com.healthsystem.xml.jaxb;

import jakarta.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
public class ClaimJaxb {

    @XmlAttribute(name = "claimRef")
    private String claimRef;

    @XmlAttribute(name = "status")
    private String status;

    @XmlElement(name = "memberRef", namespace = "http://www.healthsystem.bw/schema/healthcare")
    private String memberRef;

    @XmlElement(name = "providerRef", namespace = "http://www.healthsystem.bw/schema/healthcare")
    private String providerRef;

    @XmlElement(name = "serviceDate", namespace = "http://www.healthsystem.bw/schema/healthcare")
    private String serviceDate;

    @XmlElement(name = "totalClaimed", namespace = "http://www.healthsystem.bw/schema/healthcare")
    private BigDecimal totalClaimed;

    @XmlElement(name = "totalApproved", namespace = "http://www.healthsystem.bw/schema/healthcare")
    private BigDecimal totalApproved;

    @XmlElement(name = "memberCopay", namespace = "http://www.healthsystem.bw/schema/healthcare")
    private BigDecimal memberCopay;

    @XmlElement(name = "rejectionReason", namespace = "http://www.healthsystem.bw/schema/healthcare")
    private String rejectionReason;

    public String getClaimRef() { return claimRef; }
    public void setClaimRef(String claimRef) { this.claimRef = claimRef; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMemberRef() { return memberRef; }
    public void setMemberRef(String memberRef) { this.memberRef = memberRef; }

    public String getProviderRef() { return providerRef; }
    public void setProviderRef(String providerRef) { this.providerRef = providerRef; }

    public String getServiceDate() { return serviceDate; }
    public void setServiceDate(String serviceDate) { this.serviceDate = serviceDate; }

    public BigDecimal getTotalClaimed() { return totalClaimed; }
    public void setTotalClaimed(BigDecimal totalClaimed) { this.totalClaimed = totalClaimed; }

    public BigDecimal getTotalApproved() { return totalApproved; }
    public void setTotalApproved(BigDecimal totalApproved) { this.totalApproved = totalApproved; }

    public BigDecimal getMemberCopay() { return memberCopay; }
    public void setMemberCopay(BigDecimal memberCopay) { this.memberCopay = memberCopay; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
}
