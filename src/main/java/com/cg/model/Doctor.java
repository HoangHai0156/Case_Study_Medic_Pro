package com.cg.model;

import com.cg.model.enums.ELevel;
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
@Table(name = "doctors")
@Accessors(chain = true)
@Entity
public class Doctor extends BasePerson{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "location_region_id", referencedColumnName = "id", nullable = false)
    private LocationRegion locationRegion;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "speciality_id", referencedColumnName = "id", nullable = false)
    private Speciality speciality;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ELevel level;

}
