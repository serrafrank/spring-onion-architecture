package org.pay_my_buddy.entity.user;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.pay_my_buddy.entity.AbstractModel;
import org.pay_my_buddy.entity.Id;

import java.util.HashSet;
import java.util.Set;

/**
 * UserAggregate is a class that represents a user in the system.
 * It extends the AbstractModel class and implements the Serializable interface.
 * It contains information about the user such as first name, last name, email, password, and account.
 */
@Value
@With
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractModel<UserId> {

    /**
     * The first name of the user.
     */
    String firstName;

    /**
     * The last name of the user.
     */
    String lastName;

    /**
     * The email of the user.
     */
    Email email;

    /**
     * The password of the user.
     */
    EncodedPassword password;

    /**
     * The friends of the user.
     */
    Set<Id> friends = new HashSet<>();

    /**
     * Constructor that accepts the first name, last name, email, and password of the user.
     *
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param email     The email of the user.
     * @param password  The password of the user.
     */
    public User(@NonNull String firstName, @NonNull String lastName, @NonNull Email email, @NonNull EncodedPassword password) {
        super(UserId.of());
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /**
     * Constructor that accepts the ID, first name, last name, email, password, and friends of the user.
     *
     * @param id        The ID of the user.
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param email     The email of the user.
     * @param password  The password of the user.
     * @param friends   The friends of the user.
     */
    public User(UserId id, String firstName, String lastName, Email email, EncodedPassword password, Set<Id> friends) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        friends.forEach(this::addFriend);
    }

    public static User of(String firstName, String lastName, Email email, EncodedPassword password) {
        return new User(firstName, lastName, email, password);
    }

    /**
     * Adds a friend to the user's friend list.
     *
     * @param friendId The id of the friend to be added.
     */
    public User addFriend(@NonNull Id friendId) {
        if (id().equals(friendId)) {
            throw new IllegalArgumentException("User and friend cannot be the same");
        }

        friends.add(friendId);
        return this;
    }

    /**
     * Removes a friend from the user's friend list.
     *
     * @param friendId The id of the friend to be removed.
     */
    public User removeFriend(@NonNull Id friendId) {
        friends.remove(friendId);
        return this;
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

    /**
     * Updates the first name of the user.
     *
     * @param firstName The new first name of the user.
     */
    public User updateFirstName(@NonNull String firstName) {
        return new User(id(), firstName, lastName(), email(), password(), friends());
    }

    /**
     * Updates the last name of the user.
     *
     * @param lastName The new last name of the user.
     */
    public User updateLastName(@NonNull String lastName) {
        return new User(id(), firstName(), lastName, email(), password(), friends());
    }

    /**
     * Updates the email of the user.
     *
     * @param email The new email of the user.
     */
    public User updateEmail(@NonNull Email email) {
        return new User(id(), firstName(), lastName(), email, password(), friends());
    }

    /**
     * Updates the password of the user.
     *
     * @param password The new password of the user.
     */
    public User updatePassword(@NonNull EncodedPassword password) {
        return new User(id(), firstName(), lastName(), email(), password, friends());
    }

}