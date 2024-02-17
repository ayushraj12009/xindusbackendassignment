package com.backendAssignmentxindus;


import com.backendAssignmentxindus.Controller.AuthController;
import com.backendAssignmentxindus.Controller.AuthResponse;
import com.backendAssignmentxindus.Controller.LogginRequest;
import com.backendAssignmentxindus.Controller.WishlistController;
import com.backendAssignmentxindus.DTOs.CreateWishlistDto;
import com.backendAssignmentxindus.DTOs.GetAllUserWishList;
import com.backendAssignmentxindus.Model.User;
import com.backendAssignmentxindus.Model.Wishlists;
import com.backendAssignmentxindus.Repository.UserRepository;
import com.backendAssignmentxindus.Service.WishlistsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WishlistControllerTest {

    @Mock
    private AuthController authController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WishlistsService wishlistsService;

    @InjectMocks
    private WishlistController wishlistController;

    @Test
    public void createWishlistItemByAuthenticateUser_ValidRequest_ShouldReturnCreated() {
        // Arrange
        CreateWishlistDto createWishlistDto = new CreateWishlistDto(
                "Ayush 7",
                "description Ayush",
                1500,
                "ayushraj12009@gmail.com",
                "AyushRaj@#12009"
        );

        AuthResponse mockAuthResponse = new AuthResponse(createWishlistDto.getEmail(), createWishlistDto.getPassword());
        when(authController.signin(any(LogginRequest.class))).thenReturn(mockAuthResponse);

        User mockUser = new User();
        when(userRepository.findByEmail(anyString())).thenReturn(mockUser);

        Wishlists mockWishlist = new Wishlists(
                "Ayush 5",
                "description Ayush",
                1500);
        when(wishlistsService.createWishlist(any(Wishlists.class))).thenReturn(mockWishlist);

        // Act
        ResponseEntity responseEntity = wishlistController.creteWishListerByUser(createWishlistDto);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(mockWishlist, responseEntity.getBody());
    }



    @Test
    public void getAllWishlistByUser_ValidRequest_ShouldReturnListOfWishlists() {
        // Arrange
        GetAllUserWishList getAllUserWishList = new GetAllUserWishList("ayushraj12009@gmail.com", "AyushRaj@#12009");

        AuthResponse mockAuthResponse = new AuthResponse(getAllUserWishList.getEmail(), getAllUserWishList.getPassword());
        when(authController.signin(any(LogginRequest.class))).thenReturn(mockAuthResponse);

        List<Wishlists> mockWishlists = new ArrayList<>();
        when(wishlistsService.findAllWishListByUser(anyString())).thenReturn(mockWishlists);

        // Act
        List<Wishlists> result = wishlistController.getAllWishListByUser(getAllUserWishList);

        // Assert
        assertEquals(mockWishlists, result);
    }



    @Test
    public void findWishListById_ExistingId_ShouldReturnWishlist() {
        // Arrange
        Long id = 1L;
        Wishlists mockWishlist = new Wishlists("Ayush 5", "description Ayush", 1500);
        when(wishlistsService.findById(anyLong())).thenReturn(mockWishlist);

        // Act
        ResponseEntity result = wishlistController.findwishListById(id);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockWishlist, result.getBody());
    }


    @Test
    public void findWishlistAuthenticateUserById_ExistingIdAndAuthenticatedUser_ShouldReturnWishlist() throws Exception {
        // Arrange
        Long id = 1L;
        GetAllUserWishList userInfo = new GetAllUserWishList("ayushraj12009@gmail.com", "AyushRaj@#12009");

        AuthResponse mockAuthResponse = new AuthResponse(userInfo.getEmail(), userInfo.getPassword());
        when(authController.signin(any(LogginRequest.class))).thenReturn(mockAuthResponse);

        Wishlists mockWishlist = new Wishlists("Ayush 5", "description Ayush", 1500);
        when(wishlistsService.findByIdOnlyAuthenticateUser(anyString(), anyLong())).thenReturn(mockWishlist);

        // Act
        ResponseEntity result = wishlistController.findlist(userInfo, id);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockWishlist, result.getBody());
    }



    @Test
    public void deleteWishlistItemAuthenticateUserById_ExistingIdAndAuthenticatedUser_ShouldReturnDeleted() throws Exception {
        // Arrange
        Long id = 1L;
        GetAllUserWishList userInfo = new GetAllUserWishList("ayushraj12009@gmail.com", "AyushRaj@#12009");

        AuthResponse mockAuthResponse = new AuthResponse(userInfo.getEmail(), userInfo.getPassword());
        when(authController.signin(any(LogginRequest.class))).thenReturn(mockAuthResponse);


        when(wishlistsService.deleteByIdOnlyAuthenticateUser(anyString(), anyLong())).thenReturn("Deleted");

        // Act
        ResponseEntity result = wishlistController.deletelistByID(userInfo, id);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Deleted", result.getBody());
    }



}


