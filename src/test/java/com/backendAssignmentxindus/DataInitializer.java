package com.backendAssignmentxindus;

import com.backendAssignmentxindus.Model.User;
import com.backendAssignmentxindus.Model.Wishlists;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataInitializer implements CommandLineRunner {

    public   Map<Long, User> users = new HashMap<>();
    public   Map<Long, Wishlists> wishlists = new HashMap<>();

    @Override
    public void run(String... args) throws Exception {

        User user1 = new User("Ayush", "Raj", "AyushRaj12009", "ayushraj12009@gmail.com", "AyushRaj@#12009");
        User user2 = new User("Rahul", "Kumar", "Rahul12009", "rahul@gmail.com", "Rahul2024");
        users.put(user1.getId(), user1);
        users.put(user2.getId(), user2);


        Wishlists wishlist1 = new Wishlists("Electronics Wishlist", "Wishlist for electronic gadgets", 1000);
        Wishlists wishlist2 = new Wishlists("Books Wishlist", "Wishlist for books to read", 500);
        wishlists.put(wishlist1.getId(), wishlist1);
        wishlists.put(wishlist2.getId(), wishlist2);
    }

    public Map<Long, User> getUsers() {
        return users;
    }

    public Map<Long, Wishlists> getWishlists() {
        return wishlists;
    }
}
