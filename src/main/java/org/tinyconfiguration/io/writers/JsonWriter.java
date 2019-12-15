package org.tinyconfiguration.io.writers;

import org.tinyconfiguration.base.Configuration;
import org.tinyconfiguration.data.Property;
import org.tinyconfiguration.data.PropertyValue;
import org.tinyconfiguration.io.writers.base.Writer;

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

        if (instance.getUngrouped().size() != 0) {

            JsonArrayBuilder properties = Json.createArrayBuilder();

            for (Property p : instance.getUngrouped()) {

                JsonObjectBuilder property = Json.createObjectBuilder();

                __addProperty(p, property);

                properties.add(property);

            }

            root.add("properties", properties);

        }

        instance.getGroups().forEach(group -> {

            JsonObjectBuilder node = Json.createObjectBuilder();

            JsonArrayBuilder properties = Json.createArrayBuilder();
            JsonObjectBuilder property = Json.createObjectBuilder();

            node.add("group", group);

            instance.getGroup(group).forEach(p -> {

                __addProperty(p, property);

                properties.add(property);

            });

            node.add("properties", properties);

            nodes.add(node);

        });

        root.add("groups", nodes);

        return root.build();
    }

    private void __addProperty(Property p, JsonObjectBuilder property) {

        property.add("key", p.getKey());

        PropertyValue dt = p.getValue();

        if (dt.isArray()) {

            JsonArrayBuilder values = Json.createArrayBuilder();

            if (dt.isNumeric()) {

                if (dt.isByte()) {

                    byte[] tmp = dt.asByteArray();

                    for (byte e : tmp) {
                        values.add(e);
                    }

                } else if (dt.isShort()) {

                    short[] tmp = dt.asShortArray();

                    for (short e : tmp) {
                        values.add(e);
                    }

                } else if (dt.isInteger()) {

                    int[] tmp = dt.asIntArray();

                    for (int e : tmp) {
                        values.add(e);
                    }

                } else if (dt.isLong()) {

                    long[] tmp = dt.asLongArray();

                    for (long e : tmp) {
                        values.add(e);
                    }

                } else if (dt.isFloat()) {

                    float[] tmp = dt.asFloatArray();

                    for (float e : tmp) {
                        values.add(e);
                    }

                } else if (dt.isDouble()) {

                    double[] tmp = dt.asDoubleArray();

                    for (double e : tmp) {
                        values.add(e);
                    }

                }

            } else if (dt.isText()) {

                String[] tmp = dt.asStringArray();

                for (String e : tmp) {
                    values.add(e);
                }

            } else if (dt.isBoolean()) {

                boolean[] tmp = dt.asBooleanArray();

                for (Boolean e : tmp) {
                    values.add(e);
                }

            } else {
                values.add("Unknown");
                values.add("array");
                values.add("type");
            }

            property.add("values", values);

        } else {

            if (dt.isText()) {

                property.add("value", dt.asString());

            } else if (dt.isNumeric()) {

                if (dt.isByte()) {
                    property.add("value", dt.asByte());
                } else if (dt.isShort()) {
                    property.add("value", dt.asShort());
                } else if (dt.isInteger()) {
                    property.add("value", dt.asInt());
                } else if (dt.isLong()) {
                    property.add("value", dt.asLong());
                } else if (dt.isFloat()) {
                    property.add("value", dt.asFloat());
                } else if (dt.isDouble()) {
                    property.add("value", dt.asDouble());
                } else {
                    property.add("value", 0.0);
                }

            } else if (dt.isBoolean()) {
                property.add("value", dt.asBoolean());
            } else {
                property.add("value", "unknown datatype");
            }
        }

        if (p.getDescription() == null)
            property.addNull("description");
        else
            property.add("description", p.getDescription());

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
