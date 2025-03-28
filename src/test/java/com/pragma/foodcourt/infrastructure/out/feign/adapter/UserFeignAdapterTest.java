package com.pragma.foodcourt.infrastructure.out.feign.adapter;

import com.pragma.foodcourt.infrastructure.out.feign.client.UserFeignClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserFeignAdapterTest {

    @InjectMocks
    private UserFeignAdapter userFeignAdapter;

    @Mock
    private UserFeignClient userFeignClient;

    @Test
    void userHasRole_WhenReturnTrue() {
        Long userId = 1L;
        String roleName = "ROLE_USER";

        when(userFeignClient.userHasRole(userId, roleName))
                .thenReturn(true);

        boolean result = userFeignAdapter.userHasRole(userId, roleName);

        assertTrue(result);
    }

    @Test
    void userHasRole_WhenReturnFalse() {
        Long userId = 1L;
        String roleName = "ROLE_USER";

        when(userFeignClient.userHasRole(userId, roleName))
                .thenReturn(false);

        boolean result = userFeignAdapter.userHasRole(userId, roleName);

        assertFalse(result);
    }

    @Test
    void userHasEmail_WhenReturnTrue() {
        Long userId = 1L;
        String email = "user@mail.com";

        when(userFeignClient.userHasEmail(userId, email))
                .thenReturn(true);

        boolean result = userFeignAdapter.userHasEmail(userId, email);

        assertTrue(result);
    }

    @Test
    void userHasEmail_WhenReturnFalse() {
        Long userId = 1L;
        String email = "user@mail.com";

        when(userFeignClient.userHasEmail(userId, email))
                .thenReturn(false);

        boolean result = userFeignAdapter.userHasEmail(userId, email);

        assertFalse(result);
    }

    @Test
    void getUserIdByEmail_WhenIsSuccessful() {
        String email = "user@mail.com";

        when(userFeignClient.getUserIdByEmail(email))
                .thenReturn(1L);

        Long userId = userFeignAdapter.getUserIdByEmail(email);

        assertNotNull(userId);
        assertEquals(1L, userId);
    }

    @Test
    void getCellPhoneNumberById_WhenIsSuccessful() {
        Long userId = 1L;
        String email = "test@mail.com";

        when(userFeignClient.getCellPhoneNumberById(userId))
                .thenReturn(email);

        String result = userFeignAdapter.getCellPhoneNumberById(userId);

        assertNotNull(result);
        assertEquals(email, result);
    }
}