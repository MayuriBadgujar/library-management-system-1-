package com.backendMarch.librarymanagementsystem.Repository;

import com.backendMarch.librarymanagementsystem.Entity.LibraryCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<LibraryCard,Integer> {
}
