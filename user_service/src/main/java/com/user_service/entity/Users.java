package com.user_service.entity;

import com.user_service.util.annotations.AgeConstraint;
import com.user_service.util.annotations.NameConstraint;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NameConstraint
    private String name;
    private String occupation;
    @AgeConstraint
    private int age;
}
