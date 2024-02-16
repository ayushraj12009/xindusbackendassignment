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
import com.backendAssignmentxindus.Repository.WishlistsRepository;
import com.backendAssignmentxindus.Service.UserService;
import com.backendAssignmentxindus.Service.WishlistsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class WishlistControllerTest {

    @Mock
    private WishlistsService wishlistsService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthController authController;


    @InjectMocks
    private WishlistController wishlistController;

    @Mock
    private WishlistsRepository wishlistsRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateWishlistItemByAuthenticateUser_Success() {

        CreateWishlistDto createWishlistDto = new CreateWishlistDto(
                "itemName",
                "itemDescription",
                100,
                "ayushraj12009@gmail.com",
                "AyushRaj@#12009"

        );

        ResponseEntity<?> response = wishlistController.creteWishListerByUser(createWishlistDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


//    @Test
//    public void testFindById() {
//        Wishlists wishlists1 = new Wishlists(
//                "itemName",
//                "itemDescription",
//                100
//        );
//
//
//        Wishlists wishlists2 = new Wishlists(wishlists1.getName(), wishlists1.getDescription(), wishlists1.getPrice());
//        wishlistsRepository.save(wishlists2);
//
//        Optional<Wishlists> optionalEntity = wishlistsRepository.findById(wishlists2.getId());
//
//        assertTrue(optionalEntity.isPresent());
//        assertEquals("Test Entity", optionalEntity.get().getName());
//    }


//    @Test
//    public List<Wishlists> testgetAllWishlistByUser_Success() {
//
//        GetAllUserWishList getAllUserWishList = new GetAllUserWishList(
//                "ayushraj12009@gmail.com",
//                "AyushRaj@#12009"
//        );
//
//        ResponseEntity<?> resultResponse = (ResponseEntity<?>) wishlistController.getAllWishListByUser(getAllUserWishList);
//
//        assertEquals(HttpStatus.CREATED, resultResponse.getStatusCode());
//        return (List<Wishlists>) resultResponse;
//    }








}




