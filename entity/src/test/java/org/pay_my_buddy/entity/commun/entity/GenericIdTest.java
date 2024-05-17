package org.pay_my_buddy.entity.commun.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.pay_my_buddy.entity.UuidStub;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GenericIdTest {


    @Test
    @DisplayName("Generate Unique Id returns UUID")
    void uniqueIdGenerationReturnsUUID() {
        //GIVEN a GenericId instance
        final GenericId genericId = GenericId.of();

        //WHEN generateUniqueId is called
        final UUID uuid = genericId.generateUniqueId();

        //THEN a UUID is returned
        assertNotNull(uuid);
    }


    @Test
    @DisplayName("Id Constructor sets Id")
    void idConstructorSetsId() {
        //GIVEN an Id instance
        final Id id = new UuidStub();

        //WHEN the Id is used to create a GenericId
        final GenericId genericId = GenericId.of(id);

        //THEN the value of the Id is set to the GenericId
        assertEquals(id.value(), genericId.value());
    }

    @Test
    @DisplayName("Id Constructor sets Id with UUID")
    void idConstructorSetsIdWithUUID() {
        //GIVEN a UUID
        final UUID uuid = UUID.randomUUID();

        //WHEN the UUID is used to create a GenericId
        final GenericId genericId = GenericId.of(uuid);

        //THEN the value of the UUID is set to the GenericId
        assertEquals(uuid, genericId.value());
    }

    @Test
    @DisplayName("Id Constructor throws exception when Id is null")
    void idConstructorThrowsExceptionWhenIdIsNull() {
        //GIVEN a null Id
        final Id id = null;

        //WHEN the Id is used to create a GenericId
        Executable executable = () -> GenericId.of(id);

        //THEN an exception is thrown
        assertThrows(NullPointerException.class, executable);
    }
}