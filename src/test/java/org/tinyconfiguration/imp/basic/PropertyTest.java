package org.tinyconfiguration.imp.basic;

import org.junit.jupiter.api.Test;
import org.tinyconfiguration.abc.events.listeners.EventListener;
import org.tinyconfiguration.imp.basic.events.property.UpdateEvent;
import org.tinyconfiguration.imp.basic.events.property.base.PropertyEvent;

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

        property.onUpdateEvent().addListener(PropertyEvent.UPDATE, o1);
        property.onUpdateEvent().addListener(PropertyEvent.UPDATE, o1);

        assertEquals(2, property.onUpdateEvent().size(PropertyEvent.UPDATE));

    }

    @Test
    void removeListener() {

        EventListener<UpdateEvent> o1 = event -> {
        };

        property.onUpdateEvent().addListener(PropertyEvent.UPDATE, o1);
        property.onUpdateEvent().addListener(PropertyEvent.UPDATE, o1);

        assertEquals(2, property.onUpdateEvent().size(PropertyEvent.UPDATE));

        property.onUpdateEvent().removeListener(PropertyEvent.UPDATE, o1);

        assertEquals(1, property.onUpdateEvent().size(PropertyEvent.UPDATE));

    }
}