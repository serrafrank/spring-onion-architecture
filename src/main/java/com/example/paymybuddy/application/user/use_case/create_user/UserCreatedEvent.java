package com.example.paymybuddy.application.user.use_case.create_user;

import com.example.paymybuddy.application.Event;
import com.example.paymybuddy.application.user.domain.UserId;

public record UserCreatedEvent (UserId id, String firstName, String lastName, String email) implements Event{

}
