package com.example.paymybuddy.core.user;

import com.example.paymybuddy.core.common.entity.aggregate.AbstractAggregateEntity;
import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.user.valueobject.Account;
import com.example.paymybuddy.core.user.valueobject.UserId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.util.HashSet;
import java.util.Set;

/**
 * UserAggregate is a class that represents a user in the system.
 * It extends the AbstractAggregateEntity class and implements the Serializable interface.
 * It contains information about the user such as first name, last name, email, password, and account.
 *
 * @author serrafrank
 * @version 1.0
 * @since 2023.3.3
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class UserAggregate extends AbstractAggregateEntity<UserId> {

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The email of the user.
     */
    private String email;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The account associated with the user.
     */
    private Account account = new Account();

    /**
     * The friends of the user.
     */
    private transient Set<Id> friends = new HashSet<>();


    public UserAggregate(@NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /**
     * Adds a friend to the user's friend list.
     *
     * @param friendId The id of the friend to be added.
     */
    public void addFriend(@NonNull Id friendId) {
        friends.add(friendId);
    }

    /**
     * Removes a friend from the user's friend list.
     *
     * @param friendId The id of the friend to be removed.
     */
    public void removeFriend(@NonNull Id friendId) {
        friends.remove(friendId);
    }

    /**
     * Checks if a user is a friend of the user.
     *
     * @param friendId The id of the user to be checked.
     * @return True if the user is a friend, false otherwise.
     */
    public boolean isFriend(@NonNull Id friendId) {
        return friends.contains(friendId);
    }

}