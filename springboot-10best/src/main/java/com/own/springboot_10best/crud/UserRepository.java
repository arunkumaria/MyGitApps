package com.own.springboot_10best.crud;

package com.example.toolkit.crud;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}