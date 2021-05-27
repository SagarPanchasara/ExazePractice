package com.example.practical.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "client")
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "mobile_number", unique = true)
    private String mobileNumber;
    @Column(name = "id_number", nullable = false, unique = true)
    private String idNumber;
    @Column(name = "physical_address")
    private String physicalAddress;

}
