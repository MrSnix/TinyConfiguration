package org.tinyconfiguration.imp.basic.io;

import org.tinyconfiguration.imp.basic.Configuration;
import org.tinyconfiguration.imp.basic.Property;
import org.tinyconfiguration.imp.basic.ex.configuration.InvalidConfigurationNameException;
import org.tinyconfiguration.imp.basic.ex.configuration.InvalidConfigurationVersionException;
import org.tinyconfiguration.imp.basic.ex.configuration.MissingConfigurationIdentifiersException;

final class UtilsHandler {

    public static void asEmptyArray(Property property) {
        switch (property.getValue().getDatatype()) {
            case ARR_BOOLEAN:
                property.setValue(new boolean[0]);
                break;
            case ARR_BYTE:
                property.setValue(new byte[0]);
                break;
            case ARR_SHORT:
                property.setValue(new short[0]);
                break;
            case ARR_INT:
                property.setValue(new int[0]);
                break;
            case ARR_LONG:
                property.setValue(new long[0]);
                break;
            case ARR_FLOAT:
                property.setValue(new float[0]);
                break;
            case ARR_DOUBLE:
                property.setValue(new double[0]);
                break;
            case ARR_STRING:
                property.setValue(new String[0]);
                break;
            case ARR_CHAR:
                property.setValue(new char[0]);
                break;
        }
    }

    public static void isQualified(Configuration instance, String name, String version) throws MissingConfigurationIdentifiersException, InvalidConfigurationNameException, InvalidConfigurationVersionException {
        if (name == null)
            throw new MissingConfigurationIdentifiersException("name");

        if (version == null)
            throw new MissingConfigurationIdentifiersException("version");

        if (!name.equals(instance.getName()))
            throw new InvalidConfigurationNameException(instance.getName(), name);

        if (!version.equals(instance.getVersion()))
            throw new InvalidConfigurationVersionException(instance.getVersion(), version);
    }

}
