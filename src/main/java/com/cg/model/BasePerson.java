package com.cg.model;

import com.cg.model.enums.EGender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@MappedSuperclass
public class BasePerson extends BaseEntity{

    @Column(nullable = false, name = "full_name")
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EGender gender;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(nullable = false)
    private Date DOB;

    private String job;

    @Column(nullable = false, unique = true)
    private String identityNumber;

    @Column(nullable = false)
    private String ethnic;

}
