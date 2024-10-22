package com.example.chatTest.repository;

import com.example.chatTest.model.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
