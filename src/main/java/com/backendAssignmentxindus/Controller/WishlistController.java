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


    @PostMapping("/creatWishListItemByUserSide")
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







    @GetMapping("/findwishlistById/{id}")
    public ResponseEntity findwishListById(@PathVariable Long id){
        return ResponseEntity.ok(wishlistsService.findById(id));
    }


    @GetMapping("/findWishlist/{id}")
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
            return ResponseEntity.ok(wishlistsService.findByIdOnlyAutheticUser(userInfo.getEmail(), id));

        }catch (Exception e){
            ResponseEntity<String> stringResponseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            return stringResponseEntity;
        }


    }




    @DeleteMapping("/deleteById/{id}")
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
            return ResponseEntity.ok(wishlistsService.deleteByIdOnlyAutheticUser(userInfo.getEmail(), id));

        }catch (Exception e){
            ResponseEntity<String> stringResponseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            return stringResponseEntity;
        }


    }





//    @PostMapping("/creatWishListItemByUserSide")
//    public ResponseEntity<Wishlists> createWishlistItem(@RequestBody CreateWishlistDto createWishlistDto) {
//        LogginRequest logginRequest = new LogginRequest(createWishlistDto.getEmail(), createWishlistDto.getPassword());
//        AuthResponse authResponse = authController.signin(logginRequest);
//
//        if(authResponse == null){
//            throw new UsernameNotFoundException("User Not Found");
//        }
//
//        Wishlists wishlists1 = new Wishlists(createWishlistDto.getName(),createWishlistDto.getDescription(),createWishlistDto.getPrice());
//        Wishlists createdItem = wishlistsService.createWishListItemByUser(wishlists1);
//        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
//    }



    //    @PostMapping("/creatWishListItemByUserSide")
//    public ResponseEntity<Wishlists> createWishlistItem(@RequestBody CreateWishlistDto createWishlistDto) {
//        LogginRequest logginRequest = new LogginRequest(createWishlistDto.getEmail(), createWishlistDto.getPassword());
//        AuthResponse authResponse = authController.signin(logginRequest);
//
//        if(authResponse == null){
//            throw new UsernameNotFoundException("User Not Found");
//        }
//
//        Wishlists wishlists1 = new Wishlists(createWishlistDto.getName(),createWishlistDto.getDescription(),createWishlistDto.getPrice());
//        Wishlists createdItem = wishlistsService.createWishListItemByUser(wishlists1);
//        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
//    }


//
//    @GetMapping("/getlist/{id}")
//    public ResponseEntity<Wishlists> getWishlistItemById(@PathVariable Long id) throws Exception {
//        Wishlists wishlistItem = wishlistsService.findById(id);
//        return ResponseEntity.ok(wishlistItem);
//    }
//
//
//    @GetMapping("/getalllistbyuser")
//    public List<Wishlists> getAllWishListByUser(){
//        return wishlistsRepository.findAll();
//    }
//
//
//    @DeleteMapping("/dletelist/{id}")
//    public void deleteWishlistItemById(@PathVariable Long id) throws Exception {
//        Optional<Wishlists> optionalWishlists = wishlistsRepository.findById(id);
//
//        if(optionalWishlists.isEmpty()){
//            throw new Exception("id is not valid");
//        }
//        wishlistsRepository.deleteById(id);
//    }
//

//
//    @PostMapping("/addcreate/{userid}")
//    public Wishlists creatlistuser(@RequestBody Wishlists wishlistss, @PathVariable Long userid ){
//        try {
//            return wishlistsService.createWishlistItem(wishlistss, userid);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//



}
