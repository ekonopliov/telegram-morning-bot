package lt.kono.tmb.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void shouldBuildUser(){
        String firstName = "TestName";
        String lastName = "TestSurename";

        User user = User.builder()
                .lastName(lastName)
                .firstName(firstName)
                .build();

        Assertions.assertEquals(lastName, user.getLastName());
        Assertions.assertEquals(firstName, user.getFirstName());
    }

}