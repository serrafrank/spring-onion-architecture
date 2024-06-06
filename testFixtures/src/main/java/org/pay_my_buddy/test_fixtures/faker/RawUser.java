package org.pay_my_buddy.test_fixtures.faker;

public record RawUser(String firstName, String lastName, String email, String password) {

    public RawUser of( String firstName, String lastName, String email, String password) {
        return new RawUser(firstName, lastName, email, password);
    }

}
