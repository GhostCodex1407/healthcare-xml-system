package com.healthsystem.xml.validator;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XmlSchemaValidator {

    private final Schema schema;

    public XmlSchemaValidator(File xsdFile) throws SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        // Security: Disallow external DTDs / entity expansion
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "file,http");
        this.schema = factory.newSchema(xsdFile);
    }

    public XmlSchemaValidator(InputStream xsdStream) throws SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "file,http");
        this.schema = factory.newSchema(new StreamSource(xsdStream));
    }

    public List<ValidationError> validate(File xmlFile) {
        return performValidation(new StreamSource(xmlFile));
    }

    public List<ValidationError> validate(InputStream xmlStream) {
        return performValidation(new StreamSource(xmlStream));
    }

    private List<ValidationError> performValidation(StreamSource source) {
        List<ValidationError> errors = new ArrayList<>();
        try {
            Validator validator = schema.newValidator();
            validator.setErrorHandler(new ErrorHandler() {
                @Override
                public void warning(SAXParseException exception) {
                    errors.add(new ValidationError(
                            ValidationError.Severity.WARNING,
                            exception.getLineNumber(),
                            exception.getColumnNumber(),
                            exception.getMessage()
                    ));
                }

                @Override
                public void error(SAXParseException exception) {
                    errors.add(new ValidationError(
                            ValidationError.Severity.ERROR,
                            exception.getLineNumber(),
                            exception.getColumnNumber(),
                            exception.getMessage()
                    ));
                }

                @Override
                public void fatalError(SAXParseException exception) {
                    errors.add(new ValidationError(
                            ValidationError.Severity.FATAL_ERROR,
                            exception.getLineNumber(),
                            exception.getColumnNumber(),
                            exception.getMessage()
                    ));
                }
            });

            validator.validate(source);
        } catch (Exception e) {
            errors.add(new ValidationError(
                    ValidationError.Severity.FATAL_ERROR,
                    -1, -1,
                    "General validation error: " + e.getMessage()
            ));
        }

        return Collections.unmodifiableList(errors);
    }
}
