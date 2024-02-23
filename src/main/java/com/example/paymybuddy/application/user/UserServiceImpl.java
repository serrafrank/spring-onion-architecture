package com.example.paymybuddy.application.user;

import com.example.paymybuddy.core.common.entity.id.Id;
import com.example.paymybuddy.core.common.valueobject.Amount;
import com.example.paymybuddy.core.user.UserAggregate;
import com.example.paymybuddy.core.user.valueobject.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<UserAggregate> findUser(Id id) {
        return userRepository.findUser(id);
    }

    @Override
    public UserId createUser(String firstName, String lastName, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with email " + email + " already exists");
        }

        final UserAggregate user = new UserAggregate(
                firstName,
                lastName,
                email,
                password
        );

        return this.saveUser(user);
    }

    @Override
    public void debitUser(Id id, Amount amount) {
        UserAggregate debtor = this.getUser(id);
        debtor.getAccount().debit(amount);
        saveUser(debtor);
    }

    @Override
    public void creditUser(Id id, Amount amount) {
        UserAggregate creditor = this.getUser(id);
        creditor.getAccount().credit(amount);
        saveUser(creditor);

    }

    @Override
    public boolean existsUser(Id id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean existsUser(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean areFriends(Id user1, Id user2) {
        return this.getUser(user1).isFriend(user2);
    }


    private UserId saveUser(UserAggregate createUserCommand) {
        return userRepository.saveUser(createUserCommand);
    }


    private UserAggregate getUser(Id id) {
        return findUser(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
