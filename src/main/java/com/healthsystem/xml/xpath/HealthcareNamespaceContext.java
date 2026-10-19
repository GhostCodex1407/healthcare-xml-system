package com.healthsystem.xml.xpath;

import javax.xml.namespace.NamespaceContext;
import java.util.Collections;
import java.util.Iterator;

public class HealthcareNamespaceContext implements NamespaceContext {

    public static final String HEALTHCARE_PREFIX = "hc";
    public static final String HEALTHCARE_URI = "http://www.healthsystem.bw/schema/healthcare";

    @Override
    public String getNamespaceURI(String prefix) {
        if (HEALTHCARE_PREFIX.equals(prefix)) {
            return HEALTHCARE_URI;
        }
        return javax.xml.XMLConstants.NULL_NS_URI;
    }

    @Override
    public String getPrefix(String namespaceURI) {
        if (HEALTHCARE_URI.equals(namespaceURI)) {
            return HEALTHCARE_PREFIX;
        }
        return null;
    }

    @Override
    public Iterator<String> getPrefixes(String namespaceURI) {
        if (HEALTHCARE_URI.equals(namespaceURI)) {
            return Collections.singletonList(HEALTHCARE_PREFIX).iterator();
        }
        return Collections.emptyIterator();
    }
}
