package org.tinyconfiguration.io.writers;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.tinyconfiguration.base.Configuration;
import org.tinyconfiguration.data.Property;
import org.tinyconfiguration.data.PropertyValue;
import org.tinyconfiguration.io.writers.base.Writer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

public final class XmlWriter implements Writer<Document> {

    /**
     * This method allow to generate an object representation from the configuration instance
     *
     * @param instance The configuration instance
     * @return The object representation of the following instance
     * @throws Exception If something goes wrong during the process
     */
    @Override
    public Document toObject(Configuration instance) throws Exception {

        DocumentBuilderFactory builder = DocumentBuilderFactory.newInstance();
        // Using factory to get an instance of document builder
        DocumentBuilder db = builder.newDocumentBuilder();
        // Creating the doc representation
        Document xml = db.newDocument();

        // The root element
        Element root = xml.createElement("configuration");

        root.setAttribute("name", instance.getName());
        root.setAttribute("version", instance.getVersion());

        Element ungrouped = xml.createElement("properties");

        instance.getUngrouped().forEach(property -> {

            Element node = __addProperty(xml, property);

            ungrouped.appendChild(node);

        });

        // This will contain all elements to attach to the root
        Element groups = xml.createElement("groups");

        instance.getGroups().forEach(name -> {

            Element group = xml.createElement("group");

            group.setAttribute("name", name);

            Element properties = xml.createElement("properties");

            instance.getGroup(name).forEach(p -> {

                Element property = __addProperty(xml, p);

                properties.appendChild(property);

            });

            group.appendChild(properties);

            groups.appendChild(group);

        });

        root.appendChild(ungrouped);
        root.appendChild(groups);

        xml.appendChild(root);

        return xml;
    }

    private Element __addProperty(Document xml, Property p) {
        Element property = xml.createElement("property");

        Element key = xml.createElement("key");
        Element desc = xml.createElement("description");
        Element values;

        key.setTextContent(p.getKey());
        desc.setTextContent(p.getDescription());

        PropertyValue dt = p.getValue();

        if (dt.isArray()) {

            String[] tmp = dt.asStringArray();

            values = xml.createElement("values");

            for (String s : tmp) {
                // Create single node
                Element e = xml.createElement("value");
                // Setting text
                e.setTextContent(s);
                // Appending
                values.appendChild(e);
            }

        } else {
            // Create single node
            values = xml.createElement("value");
            // Setting text
            values.setTextContent(dt.asString());
        }

        if (p.getDescription() == null)
            desc.setTextContent("null");
        else
            desc.setTextContent(p.getDescription());

        property.appendChild(key);
        property.appendChild(desc);
        property.appendChild(values);
        return property;
    }

    /**
     * This method allow to generate a file given any object representation of the configuration instance
     *
     * @param instance The configuration instance
     * @throws Exception If something goes wrong during the process
     * @see Writer#toObject(Configuration)
     */
    @Override
    public void toFile(Configuration instance) throws Exception {

        Document obj = this.toObject(instance);

        OutputFormat format = new OutputFormat();

        format.setLineWidth(120);
        format.setIndenting(true);
        format.setIndent(4);
        format.setEncoding(StandardCharsets.UTF_8.name());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(instance.getFile()))) {

            XMLSerializer srx = new XMLSerializer(bw, format);

            srx.serialize(obj);

        }
    }
}
