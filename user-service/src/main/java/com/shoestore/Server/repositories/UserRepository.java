package com.shoestore.Server.repositories;

import com.shoestore.Server.dto.UserDTO;
import com.shoestore.Server.dto.UserResponseDTO;
import com.shoestore.Server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    public User findByEmail(String email);

    // Tìm kiếm theo tên, không phân biệt hoa thường
    List<User> findByNameContainingIgnoreCase(String search);

    // Tìm kiếm theo trạng thái và tên, không phân biệt hoa thường
    List<User> findByStatusAndNameContainingIgnoreCase(String status, String search);

    // Truy vấn tùy chỉnh kết hợp với Role
    @Query("SELECT u FROM User u LEFT JOIN u.role r WHERE " +
            "(:name IS NULL OR LOWER(u.userName) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:roleName IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :roleName, '%'))) AND " +
            "(:status IS NULL OR LOWER(u.status) = LOWER(:status))")
    List<User> searchUsers(
            @Param("name") String name,
            @Param("roleName") String roleName,
            @Param("status") String status
    );

        // Này của hiếu
    @Query("SELECT u FROM User u JOIN FETCH u.addresses WHERE u.role.name = :roleName")
    List<User> findUsersWithAddressesByRoleName(@Param("roleName") String roleName);
    @Query("SELECT u FROM User u JOIN FETCH u.addresses WHERE u.role.name = :roleName AND u.userID = :id")
    List<User> findByRoleAndId(@Param("roleName") String roleName, @Param("id") int id);

}
