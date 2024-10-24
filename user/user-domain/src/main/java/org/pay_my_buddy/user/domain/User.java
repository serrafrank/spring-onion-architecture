package org.pay_my_buddy.user.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.pay_my_buddy.shared.domain.api.DomainEvent;
import org.pay_my_buddy.shared.domain.entity.AbstractDomainEntity;
import org.pay_my_buddy.shared.domain.entity.Id;

import java.util.HashSet;
import java.util.Set;

@Accessors(fluent = true, chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends AbstractDomainEntity {

    private final UserId id;
    private final String firstname;
    private final String lastname;
    private final String email;
    private final String password;

    Set<Id> friends = new HashSet<>();

    public static User of(UserId id) {
        return new User(id, "", "", "", "");
    }

    public static User of(UserId id, String firstname, String lastname, String email, String password) {
        return new User(id, firstname, lastname, email, password);
    }

    public static User create(UserId id, String firstname, String lastname, String email, String password) {
        var user = User.of(id, firstname, lastname, email, password);
        user.addDomainEvent(new UserCreatedDomainEvent(user.id));
        return user;
    }

    public User delete() {
        this.addDomainEvent(new UserDeletedDomainEvent(id));
        return this;
    }

    public record UserCreatedDomainEvent(UserId id) implements DomainEvent {
    }

    private record UserDeletedDomainEvent(UserId id) implements DomainEvent {
    }
}