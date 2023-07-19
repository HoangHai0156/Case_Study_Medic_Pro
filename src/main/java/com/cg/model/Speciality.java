package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "specialities")
@Accessors(chain = true)
public class Speciality extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, name = "code_name")
    private String codeName;

    @Column(unique = true, nullable = false)
    private String name;

//    @OneToMany
//    private List<Doctor> doctors;
//
//    @OneToMany
//    private List<Room> rooms;
}
