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
           LogginRequest logginRequest = new LogginRequest(createWishlistDto.getEmail(), createWishlistDto.getPassword());
           AuthResponse authResponse = authController.signin(logginRequest);

           if(authResponse.getToken().isEmpty()){
               throw new UsernameNotFoundException("User Not Found");
           }
       }catch (Exception e){
           return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
       }

       User user = userRepository.findByEmail(createWishlistDto.getEmail());

        Wishlists wishlists1 = new Wishlists(createWishlistDto.getName(),createWishlistDto.getDescription(),createWishlistDto.getPrice());

       wishlists1.setUser(user);

        Wishlists createdItem = wishlistsService.createWishlist(wishlists1);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @GetMapping("/getAllWishlistByUser")
    public List<Wishlists> creteWishListerByUser(@RequestBody GetAllUserWishList getAllUserWishList) {
        try {
            LogginRequest logginRequest = new LogginRequest(getAllUserWishList.getEmail(), getAllUserWishList.getPassword());
            AuthResponse authResponse = authController.signin(logginRequest);

            if (authResponse.getToken().isEmpty()) {
                throw new UsernameNotFoundException("User Not Found");
            }
        } catch (Exception e) {
            ResponseEntity<String> stringResponseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            return (List<Wishlists>) stringResponseEntity;
        }
        return wishlistsService.findAllWishListByUser(getAllUserWishList.getEmail());
    }

    @GetMapping("/findWishlisForAllID/{id}")
    public ResponseEntity findwishListById(@PathVariable Long id){
        return ResponseEntity.ok(wishlistsService.findById(id));
    }

    @GetMapping("/findWishlistAuthenticateUserById/{id}")
    public ResponseEntity findlist(@RequestBody GetAllUserWishList userInfo, @PathVariable Long id) throws Exception {
        try {
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
            return ResponseEntity.ok(wishlistsService.findByIdOnlyAuthenticateUser(userInfo.getEmail(), id));

        }catch (Exception e){
            ResponseEntity<String> stringResponseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            return stringResponseEntity;
        }
    }

    @DeleteMapping("/deleteWishlistItemAuthenticateUserById/{id}")
    public ResponseEntity deletelistByID(@RequestBody GetAllUserWishList userInfo, @PathVariable Long id) throws Exception {
        try {
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
            return ResponseEntity.ok(wishlistsService.deleteByIdOnlyAuthenticateUser(userInfo.getEmail(), id));

        }catch (Exception e){
            ResponseEntity<String> stringResponseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            return stringResponseEntity;
        }
    }

}
