package org.pay_my_buddy.entity.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pay_my_buddy.entity.GenericId;
import org.pay_my_buddy.entity.Id;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountIdTest {

    @Test
    @DisplayName("Can create an AccountId object with a non-empty Id")
    void can_create_an_account_id_object_with_a_non_empty_id() {
        // Given
        Id id = GenericId.of();

        // When
        AccountId accountId = AccountId.of(id);

        // Then
        assertNotNull(accountId);
        assertEquals(id.value(), accountId.value());
    }

    @Test
    @DisplayName("Can create an AccountId object with a non-empty UUID")
    void can_create_an_account_id_object_with_a_non_empty_uuid() {
        // Given
        UUID uuid = UUID.randomUUID();

        // When
        AccountId accountId = AccountId.of(uuid);

        // Then
        assertNotNull(accountId);
        assertEquals(uuid, accountId.value());
    }

    @Test
    @DisplayName("Can create an AccountId object with a generated Id")
    void can_create_an_account_id_object_with_a_generated_id() {
        // When
        AccountId accountId = AccountId.of();

        // Then
        assertNotNull(accountId);
    }
}