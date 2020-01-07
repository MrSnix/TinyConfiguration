package org.tinyconfiguration.imp.basic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertyTest {

    private final Property property;

    public PropertyTest() {
        this.property = new Property.Builder().
                setKey("language").
                setValue("EN").
                setDescription("Specifies the language environment for the session").
                build();
    }

    @Test
    void getValue() {

        assertEquals("EN", property.getValue().asString());

        assertThrows(IllegalArgumentException.class, () -> {
            property.getValue().setValue(5);
        });

    }
}