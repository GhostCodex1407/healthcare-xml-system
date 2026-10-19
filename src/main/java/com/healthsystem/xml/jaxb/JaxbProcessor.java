package com.healthsystem.xml.jaxb;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;

public class JaxbProcessor {

    private final JAXBContext jaxbContext;

    public JaxbProcessor() throws JAXBException {
        this.jaxbContext = JAXBContext.newInstance(HealthcareDatabaseJaxb.class);
    }

    public HealthcareDatabaseJaxb unmarshal(File xmlFile) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (HealthcareDatabaseJaxb) unmarshaller.unmarshal(xmlFile);
    }

    public HealthcareDatabaseJaxb unmarshal(InputStream xmlStream) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (HealthcareDatabaseJaxb) unmarshaller.unmarshal(xmlStream);
    }

    public void marshal(HealthcareDatabaseJaxb database, File outputFile) throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(database, outputFile);
    }

    public String marshalToString(HealthcareDatabaseJaxb database) throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter writer = new StringWriter();
        marshaller.marshal(database, writer);
        return writer.toString();
    }
}
