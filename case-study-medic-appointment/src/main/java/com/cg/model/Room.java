package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "rooms")
@Accessors(chain = true)
@Entity
public class Room extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "speciality_id", referencedColumnName = "id", nullable = false)
    private Speciality speciality;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, name = "is_available")
    private Boolean isAvailable;

//    @OneToMany
//    private List<Appointment> appointments;
}
