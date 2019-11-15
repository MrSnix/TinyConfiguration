package org.tinyconfiguration.io.impl;

import org.tinyconfiguration.base.Configuration;
import org.tinyconfiguration.data.base.Datatype;
import org.tinyconfiguration.io.Writer;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class JsonWriter implements Writer<JsonObject> {

    @Override
    public JsonObject toObject(Configuration instance) {

        JsonObjectBuilder root = Json.createObjectBuilder();
        JsonArrayBuilder nodes = Json.createArrayBuilder();

        root.add("name", instance.getName());
        root.add("version", instance.getVersion());

        instance.getGroups().forEach(group -> {

            JsonObjectBuilder node = Json.createObjectBuilder();

            JsonArrayBuilder properties = Json.createArrayBuilder();
            JsonObjectBuilder property = Json.createObjectBuilder();

            node.add("group", group);

            instance.get(group).forEach(p -> {

                property.add("key", p.getKey());

                Datatype dt = p.getValue();

                if (dt.isArray()) {

                    JsonArrayBuilder values = Json.createArrayBuilder();
                    String[] tmp = dt.asArray().asStringArray();

                    for (String s : tmp) {
                        values.add(s);
                    }

                    property.add("values", values);

                } else {
                    property.add("value", dt.asValue().asString());
                }

                if (p.getDescription() == null)
                    property.addNull("description");
                else
                    property.add("description", p.getDescription());

                properties.add(property);

            });

            node.add("properties", properties);

            nodes.add(node);

        });

        root.add("groups", nodes);

        return root.build();
    }

    @Override
    public void toFile(Configuration instance) throws IOException {
        Map<String, Object> options = new HashMap<>(1);
        options.put(JsonGenerator.PRETTY_PRINTING, true);

        JsonWriterFactory writerFactory = Json.createWriterFactory(options);
        JsonObject obj = this.toObject(instance);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(instance.getFile()));
             javax.json.JsonWriter writer = writerFactory.createWriter(bw)) {

            writer.writeObject(obj);

        }
    }
}
