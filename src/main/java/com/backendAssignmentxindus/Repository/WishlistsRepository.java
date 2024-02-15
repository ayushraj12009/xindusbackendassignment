package com.backendAssignmentxindus.Repository;

import com.backendAssignmentxindus.Model.Wishlists;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistsRepository extends JpaRepository<Wishlists, Long> {

    List<Wishlists> findByUserId(Long userId);

    Wishlists findByIdAndUserId(Long wishlistItemId, Long userId);
}
