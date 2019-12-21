package org.tinyconfiguration.io.readers;

import org.tinyconfiguration.base.Configuration;
import org.tinyconfiguration.data.Property;
import org.tinyconfiguration.exceptions.InvalidConfigurationNameException;
import org.tinyconfiguration.exceptions.InvalidConfigurationVersionException;
import org.tinyconfiguration.exceptions.MissingConfigurationPropertyException;
import org.tinyconfiguration.exceptions.UnknownConfigurationPropertyException;
import org.tinyconfiguration.io.readers.base.Reader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class JsonReader implements Reader {

    private Configuration instance;
    private File file;
    private JsonObject configuration;

    public JsonReader(Configuration instance) {
        this.instance = instance;
        this.file = instance.getFile();
    }

    /**
     * This method generate an object representation of the configuration from the file
     */
    @Override
    public void toObject() throws
            FileNotFoundException,
            InvalidConfigurationNameException,
            InvalidConfigurationVersionException,
            MissingConfigurationPropertyException,
            UnknownConfigurationPropertyException {

        // Reading the file from the disk
        this.configuration = read(instance.getFile());

        // Acquiring some basic info
        String name = configuration.getString("name");
        String version = configuration.getString("version");

        // Basic checks
        if (!name.equals(instance.getName()))
            throw new InvalidConfigurationNameException(instance.getName(), name);

        if (!version.equals(instance.getVersion()))
            throw new InvalidConfigurationVersionException(instance.getVersion(), version);

        load();

    }

    private JsonObject read(File f) throws FileNotFoundException {

        InputStream fis = new FileInputStream(f);

        javax.json.JsonReader reader = Json.createReader(fis);
        JsonObject obj = reader.readObject();
        reader.close();

        return obj;
    }

    private void load() throws MissingConfigurationPropertyException, UnknownConfigurationPropertyException {
        ungrouped();
    }

    private void ungrouped() throws MissingConfigurationPropertyException, UnknownConfigurationPropertyException {

        JsonArray properties = configuration.getJsonArray("properties");

        if (properties == null && !instance.getUngrouped().isEmpty())
            throw new MissingConfigurationPropertyException(instance.getUngrouped());
        else if ((properties != null) && !instance.getUngrouped().isEmpty()) {
            List<Property> ungrouped = decode(properties);
        }


    }

    private List<Property> decode(JsonArray properties) {

        final List<Property> decoded = new ArrayList<>();

        properties.forEach(obj -> {

            Property p;

            JsonObject property = obj.asJsonObject();

            switch (property.get("value").getValueType()) {
                case ARRAY:
                    p = __decode_array(property);
                    break;
                case OBJECT:
                    p = __decode_obj(property);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + property.get("value").getValueType());
            }

            decoded.add(p);

        });

        return decoded;
    }

    private Property __decode_obj(JsonObject obj) {

        Property.Builder p = new Property.Builder();

        p.setKey(obj.getString("key"));

        switch (obj.getValueType()) {
            case STRING:
                p.setValue(obj.getString("value"));
                break;
            case NUMBER:
                JsonNumber value = obj.getJsonNumber("value");

                if (value.isIntegral()) {

                    BigInteger i = value.bigIntegerValue();

                } else {
                    BigDecimal d = value.bigDecimalValue();
                }

                break;
            case TRUE:
            case FALSE:
                p.setValue(obj.getBoolean("value"));
                break;
            case NULL:
                // TODO So, what?
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + obj.getValueType());
        }

        return null;
    }

    private Property __decode_array(JsonObject obj) {
        return null;
    }

}
