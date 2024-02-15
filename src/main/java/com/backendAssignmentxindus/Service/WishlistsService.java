package com.backendAssignmentxindus.Service;

import com.backendAssignmentxindus.Controller.AuthController;
import com.backendAssignmentxindus.Model.User;
import com.backendAssignmentxindus.Model.Wishlists;
import com.backendAssignmentxindus.Repository.UserRepository;
import com.backendAssignmentxindus.Repository.WishlistsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistsService {


    @Autowired
    private WishlistsRepository wishlistsRepository;


    @Autowired
    private AuthController authController;


    @Autowired
    private UserRepository userRepository;

    public Wishlists createWishlist(Wishlists wishlist) {
        return wishlistsRepository.save(wishlist);
    }


    public List<Wishlists> findAllWishListByUser(String email){
        User user = userRepository.findByEmail(email);
        return user.getWishlists();
    }


    public Wishlists findById(Long id){
        return wishlistsRepository.findById(id).get();
    }

    public Wishlists findByIdOnlyAuthenticateUser(String email, Long id) throws Exception{
        User user = userRepository.findByEmail(email);
        Optional<Wishlists> wishlists = wishlistsRepository.findById(id);

        if (wishlists.isEmpty()){
            throw new Exception("Wishlist is not available with this ID");
        }

        if (!user.getWishlists().contains(wishlists.get())){
            throw  new Exception("This Wishlist Item is not match with the user");
        }

        return wishlists.get();

    }


    public String deleteByIdOnlyAuthenticateUser(String email, Long id) throws Exception {
        // Find the user by email
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }

        // Find the wishlist item by id
        Optional<Wishlists> optionalWishlist = wishlistsRepository.findById(id);
        if (optionalWishlist.isEmpty()) {
            throw new Exception("Wishlist item not found");
        }
        Wishlists wishlist = optionalWishlist.get();

        // Check if the wishlist item belongs to the user
        if (!wishlist.getUser().equals(user)) {
            throw new Exception("Wishlist item does not belong to the user");
        }

        // Remove the wishlist item from the user's wishlists list
        List<Wishlists> userWishlists = user.getWishlists();
        userWishlists.remove(wishlist);
        user.setWishlists(userWishlists);
        userRepository.save(user);

        // Delete the wishlist item from the database
        wishlistsRepository.deleteById(id);

        return "Wishlist item deleted successfully";
    }

}
