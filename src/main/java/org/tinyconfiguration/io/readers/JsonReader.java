package org.tinyconfiguration.io.readers;

import org.tinyconfiguration.base.Configuration;
import org.tinyconfiguration.io.readers.base.Reader;

import java.io.IOException;

public class JsonReader implements Reader {
    /**
     * This method generate an object representation of the configuration from the file
     *
     * @param instance The configuration instance on to perform the loading process
     * @throws IOException If something goes wrong during the process
     */
    @Override
    public void toObject(Configuration instance) throws IOException {

    }
}
