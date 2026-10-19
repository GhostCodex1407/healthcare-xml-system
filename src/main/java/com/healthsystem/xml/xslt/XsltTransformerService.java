package com.healthsystem.xml.xslt;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;

public class XsltTransformerService {

    private final TransformerFactory transformerFactory;

    public XsltTransformerService() {
        this.transformerFactory = TransformerFactory.newInstance();
    }

    public void transform(File xmlFile, File xsltFile, File outputHtmlFile) throws Exception {
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(xsltFile));
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        if (outputHtmlFile.getParentFile() != null && !outputHtmlFile.getParentFile().exists()) {
            outputHtmlFile.getParentFile().mkdirs();
        }

        transformer.transform(new StreamSource(xmlFile), new StreamResult(outputHtmlFile));
    }

    public String transformToString(InputStream xmlStream, InputStream xsltStream) throws Exception {
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(xsltStream));
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        StringWriter writer = new StringWriter();
        transformer.transform(new StreamSource(xmlStream), new StreamResult(writer));
        return writer.toString();
    }
}
