package org.tinyconfiguration.imp.basic;

import org.junit.jupiter.api.Test;
import org.tinyconfiguration.abc.events.listeners.EventListener;
import org.tinyconfiguration.imp.basic.events.base.PropertyEvent;
import org.tinyconfiguration.imp.basic.events.property.UpdateEvent;

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

    @Test
    void addListener() {

        EventListener<UpdateEvent> o1 = event -> {
        };

        property.addListener(PropertyEvent.UPDATE, o1);
        property.addListener(PropertyEvent.UPDATE, o1);

        assertEquals(2, property.getListeners(PropertyEvent.UPDATE).size());

    }

    @Test
    void removeListener() {

        EventListener<UpdateEvent> o1 = event -> {
        };

        property.addListener(PropertyEvent.UPDATE, o1);
        property.addListener(PropertyEvent.UPDATE, o1);

        assertEquals(2, property.getListeners(PropertyEvent.UPDATE).size());

        property.removeListener(PropertyEvent.UPDATE, o1);

        assertEquals(1, property.getListeners(PropertyEvent.UPDATE).size());

    }
}