package com.backendAssignmentxindus.Controller;


import com.backendAssignmentxindus.DTOs.CreateWishlistDto;
import com.backendAssignmentxindus.DTOs.GetAllUserWishList;
import com.backendAssignmentxindus.Model.User;
import com.backendAssignmentxindus.Model.Wishlists;
import com.backendAssignmentxindus.Repository.UserRepository;
import com.backendAssignmentxindus.Repository.WishlistsRepository;
import com.backendAssignmentxindus.Service.UserService;
import com.backendAssignmentxindus.Service.WishlistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class WishlistController {


    @Autowired
    private WishlistsService wishlistsService;

    @Autowired
    private UserService userService;

    @Autowired
    private WishlistsRepository wishlistsRepository;

    @Autowired
    private AuthController authController;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/createWishlistItemByAuthenticateUser")
    public ResponseEntity creteWishListerByUser(@RequestBody CreateWishlistDto createWishlistDto){
       try {
           // Creating a login request object with email and password from the request body that is present in database
           LogginRequest logginRequest = new LogginRequest(createWishlistDto.getEmail(), createWishlistDto.getPassword());
           AuthResponse authResponse = authController.signin(logginRequest);

           if(authResponse.getToken().isEmpty()){
               throw new UsernameNotFoundException("User Not Found");
           }
       }catch (Exception e){
           return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
       }

        // checking the user by email from the repository that is present or not
       User user = userRepository.findByEmail(createWishlistDto.getEmail());

        // Creating a wishlist item object with details from the request body
       Wishlists wishlists1 = new Wishlists(createWishlistDto.getName(),createWishlistDto.getDescription(),createWishlistDto.getPrice());

       // Set the user for the wishlist item
       wishlists1.setUser(user);

        // Creating the wishlist item using the service
        Wishlists createdItem = wishlistsService.createWishlist(wishlists1);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @GetMapping("/getAllWishlistByUser")
    public List<Wishlists> getAllWishListByUser(@RequestBody GetAllUserWishList getAllUserWishList) {

        try {
            // Creating a login request object with email and password from the request body that is present in database
            LogginRequest logginRequest = new LogginRequest(getAllUserWishList.getEmail(), getAllUserWishList.getPassword());
            AuthResponse authResponse = authController.signin(logginRequest);

            if (authResponse.getToken().isEmpty()) {
                throw new UsernameNotFoundException("User Not Found");
            }
        } catch (Exception e) {
            ResponseEntity<String> stringResponseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            return (List<Wishlists>) stringResponseEntity;
        }
        // if login request is valid then return all the wishlist that is created by user
        return wishlistsService.findAllWishListByUser(getAllUserWishList.getEmail());
    }

    @GetMapping("/findWishlisForAllID/{id}")
    public ResponseEntity findwishListById(@PathVariable Long id){
        // this is for all wishlist any wishlist that is present in DB they can access this is additional APIs
        return ResponseEntity.ok(wishlistsService.findById(id));
    }

    @GetMapping("/findWishlistAuthenticateUserById/{id}")
    public ResponseEntity findlist(@RequestBody GetAllUserWishList userInfo, @PathVariable Long id) throws Exception {
        try {
            // Creating a login request object with email and password from the request body that is present in database
            LogginRequest logginRequest = new LogginRequest(userInfo.getEmail(), userInfo.getPassword());
            AuthResponse authResponse = authController.signin(logginRequest);

            if (authResponse.getToken().isEmpty()) {
                throw new UsernameNotFoundException("User Not Found");
            }
        } catch (Exception e) {
            ResponseEntity<String> stringResponseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            return stringResponseEntity;
        }
        try {
            // Try to find the wishlist item by ID for the authenticated user
            return ResponseEntity.ok(wishlistsService.findByIdOnlyAuthenticateUser(userInfo.getEmail(), id));

        }catch (Exception e){
            // Return a bad request response with the error message if any exception occurs during finding the wishlist item
            ResponseEntity<String> stringResponseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            return stringResponseEntity;
        }
    }

    @DeleteMapping("/deleteWishlistItemAuthenticateUserById/{id}")
    public ResponseEntity deletelistByID(@RequestBody GetAllUserWishList userInfo, @PathVariable Long id) throws Exception {
        try {
            // Creating a login request object with email and password from the request body that is present in database
            LogginRequest logginRequest = new LogginRequest(userInfo.getEmail(), userInfo.getPassword());
            AuthResponse authResponse = authController.signin(logginRequest);

            if (authResponse.getToken().isEmpty()) {
                throw new UsernameNotFoundException("User Not Found");
            }
        } catch (Exception e) {
            ResponseEntity<String> stringResponseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            return stringResponseEntity;
        }
        try {
            // Try to delete the wishlist item by ID for the authenticated user
            return ResponseEntity.ok(wishlistsService.deleteByIdOnlyAuthenticateUser(userInfo.getEmail(), id));

        }catch (Exception e){
            // Return a bad request response with the error message if any exception occurs during deleting the wishlist item
            ResponseEntity<String> stringResponseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            return stringResponseEntity;
        }
    }

}
