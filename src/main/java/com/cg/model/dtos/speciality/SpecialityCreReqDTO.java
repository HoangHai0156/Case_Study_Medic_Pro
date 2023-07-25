package com.cg.model.dtos.speciality;

import com.cg.model.Speciality;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SpecialityCreReqDTO{

    private String codeName;

    private String name;

    public Speciality toSpeciality(){

        return new Speciality()
                .setId(null)
                .setCodeName(codeName)
                .setName(name);
    }


}
