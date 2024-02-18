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
        //creating a wishlist item
        CreateWishlistDto createWishlistDto = new CreateWishlistDto(
                "Ayush 7",
                "description Ayush",
                1500,
                "ayushraj12009@gmail.com",
                "AyushRaj@#12009"
        );

        // Mock authentication response
        AuthResponse mockAuthResponse = new AuthResponse(createWishlistDto.getEmail(), createWishlistDto.getPassword());
        when(authController.signin(any(LogginRequest.class))).thenReturn(mockAuthResponse);

        // Mock wishlist item
        User mockUser = new User();
        when(userRepository.findByEmail(anyString())).thenReturn(mockUser);

        Wishlists mockWishlist = new Wishlists(
                "Ayush 5",
                "description Ayush",
                1500);
        when(wishlistsService.createWishlist(any(Wishlists.class))).thenReturn(mockWishlist);

        // Act
        // Calling the method to create a wishlist item
        ResponseEntity responseEntity = wishlistController.creteWishListerByUser(createWishlistDto);

        // Assert
        // Checking if the response status code is HttpStatus.CREATED
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Checking if the response body matches the mocked wishlist item
        assertEquals(mockWishlist, responseEntity.getBody());
    }



    @Test
    public void getAllWishlistByUser_ValidRequest_ShouldReturnListOfWishlists() {
        // Arrange
        // Getting all wishlists by user
        GetAllUserWishList getAllUserWishList = new GetAllUserWishList("ayushraj12009@gmail.com", "AyushRaj@#12009");

        // Mock authentication response
        AuthResponse mockAuthResponse = new AuthResponse(getAllUserWishList.getEmail(), getAllUserWishList.getPassword());
        when(authController.signin(any(LogginRequest.class))).thenReturn(mockAuthResponse);

        // Mock list of wishlists
        List<Wishlists> mockWishlists = new ArrayList<>();
        when(wishlistsService.findAllWishListByUser(anyString())).thenReturn(mockWishlists);

        // Act
        // Calling the method to get all wishlists by user
        List<Wishlists> result = wishlistController.getAllWishListByUser(getAllUserWishList);

        // Assert
        // Checking if the result matches the mocked list of wishlists
        assertEquals(mockWishlists, result);
    }



    @Test
    public void findWishListById_ExistingId_ShouldReturnWishlist() {
        // Arrange
        // checking an existing wishlist ID
        Long id = 1L;
        Wishlists mockWishlist = new Wishlists("Ayush 5", "description Ayush", 1500);
        when(wishlistsService.findById(anyLong())).thenReturn(mockWishlist);

        // Act
        // Calling the method to find a wishlist by ID
        ResponseEntity result = wishlistController.findwishListById(id);

        // Assert
        // Checking if the response status code is HttpStatus.OK
        assertEquals(HttpStatus.OK, result.getStatusCode());
        // Checking if the returned wishlist matches the mocked wishlist
        assertEquals(mockWishlist, result.getBody());
    }


    @Test
    public void findWishlistAuthenticateUserById_ExistingIdAndAuthenticatedUser_ShouldReturnWishlist() throws Exception {
        // Arrange
        // checking an existing wishlist ID
        Long id = 1L;
        //user information for authentication
        GetAllUserWishList userInfo = new GetAllUserWishList("ayushraj12009@gmail.com", "AyushRaj@#12009");

        // Mock authentication response
        AuthResponse mockAuthResponse = new AuthResponse(userInfo.getEmail(), userInfo.getPassword());
        when(authController.signin(any(LogginRequest.class))).thenReturn(mockAuthResponse);

        // Mock a wishlist object
        Wishlists mockWishlist = new Wishlists("Ayush 5", "description Ayush", 1500);
        when(wishlistsService.findByIdOnlyAuthenticateUser(anyString(), anyLong())).thenReturn(mockWishlist);

        // Act
        // Calling the method to find a wishlist by ID for an authenticated user
        ResponseEntity result = wishlistController.findlist(userInfo, id);

        // Assert
        // Checking if the response status code is HttpStatus.OK
        assertEquals(HttpStatus.OK, result.getStatusCode());
        // Checking if the returned wishlist matches the mocked wishlist
        assertEquals(mockWishlist, result.getBody());
    }



    @Test
    public void deleteWishlistItemAuthenticateUserById_ExistingIdAndAuthenticatedUser_ShouldReturnDeleted() throws Exception {
        // Arrange
        // An existing wishlist ID
        Long id = 1L;

       // user information for authentication
        GetAllUserWishList userInfo = new GetAllUserWishList("ayushraj12009@gmail.com", "AyushRaj@#12009");

        // Mock authentication response
        AuthResponse mockAuthResponse = new AuthResponse(userInfo.getEmail(), userInfo.getPassword());
        when(authController.signin(any(LogginRequest.class))).thenReturn(mockAuthResponse);

        // Mock deletion operation result
        when(wishlistsService.deleteByIdOnlyAuthenticateUser(anyString(), anyLong())).thenReturn("Deleted");

        // Act
        // Calling the method to delete a wishlist item by ID for an authenticated user
        ResponseEntity result = wishlistController.deletelistByID(userInfo, id);

        // Assert
        // Checking if the response status code is HttpStatus.OK
        assertEquals(HttpStatus.OK, result.getStatusCode());
        // Checking if the deletion message matches the expected value
        assertEquals("Deleted", result.getBody());
    }



}


