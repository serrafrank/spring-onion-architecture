package org.pay_my_buddy.modules.user.query.infrastructure;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.pay_my_buddy.modules.user.shared.UserEntityProjection;
import org.pay_my_buddy.modules.user.shared.UserId;
import org.pay_my_buddy.modules.user.shared.UserState;
import org.pay_my_buddy.modules.user.shared.command.UserCreatedEvent;

import java.util.Set;
import java.util.stream.Collectors;

@Table
@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Accessors(fluent = true)
public class UserEntity implements UserEntityProjection {

    @Id
    private String id;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserState currentState;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "friends", joinColumns = @JoinColumn(name = "users_id"))
    @Column(name = "friendId", nullable = false)
    private Set<String> friends;

    public UserEntity(UserCreatedEvent event) {
        id = event.id().value();
        firstname = event.firstname();
        lastname = event.lastname();
        email = event.email();
        password = event.password();
        currentState = event.currentState();
    }


    @Override
    public UserId userId() {
        return new UserId(id);
    }


    @Override
    public Set<UserId> friends() {
        return friends.stream().map(UserId::new).collect(Collectors.toSet());
    }

    public UserEntity addFriend(UserId friend) {
        friends.add(friend.value());
        return this;
    }

    public UserEntity removeFriend(UserId friend) {
        friends.remove(friend.value());
        return this;
    }
}
