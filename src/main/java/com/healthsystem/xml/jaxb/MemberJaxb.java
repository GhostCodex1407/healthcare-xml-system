package com.healthsystem.xml.jaxb;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class MemberJaxb {

    @XmlAttribute(name = "membershipNumber")
    private String membershipNumber;

    @XmlAttribute(name = "status")
    private String status;

    @XmlElement(name = "omangNumber", namespace = "http://www.healthsystem.bw/schema/healthcare")
    private String omangNumber;

    @XmlElement(name = "firstName", namespace = "http://www.healthsystem.bw/schema/healthcare")
    private String firstName;

    @XmlElement(name = "lastName", namespace = "http://www.healthsystem.bw/schema/healthcare")
    private String lastName;

    @XmlElement(name = "gender", namespace = "http://www.healthsystem.bw/schema/healthcare")
    private String gender;

    @XmlElement(name = "dateOfBirth", namespace = "http://www.healthsystem.bw/schema/healthcare")
    private String dateOfBirth;

    @XmlElement(name = "enrolledPlanRef", namespace = "http://www.healthsystem.bw/schema/healthcare")
    private String enrolledPlanRef;

    public String getMembershipNumber() { return membershipNumber; }
    public void setMembershipNumber(String membershipNumber) { this.membershipNumber = membershipNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getOmangNumber() { return omangNumber; }
    public void setOmangNumber(String omangNumber) { this.omangNumber = omangNumber; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getEnrolledPlanRef() { return enrolledPlanRef; }
    public void setEnrolledPlanRef(String enrolledPlanRef) { this.enrolledPlanRef = enrolledPlanRef; }

    public String getFullName() {
        return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
    }
}
