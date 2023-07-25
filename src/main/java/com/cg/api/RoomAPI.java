package com.cg.api;

import com.cg.exception.DataInputException;
import com.cg.model.Room;
import com.cg.model.Speciality;
import com.cg.model.dtos.room.RoomCreReqDTO;
import com.cg.model.dtos.room.RoomResDTO;
import com.cg.service.room.IRoomService;
import com.cg.service.speciality.ISpecialityService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomAPI {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ISpecialityService specialityService;

    @Autowired
    private IRoomService roomService;


    @GetMapping
    public ResponseEntity<?> getAll(){
        List<RoomResDTO> roomResDTOList = new ArrayList<>();
        List<Room> rooms = roomService.findAll();

        for (Room room: rooms){
            RoomResDTO roomResDTO = room.toRoomResDTO();
            roomResDTOList.add(roomResDTO);
        }

        return new ResponseEntity<>(roomResDTOList,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RoomCreReqDTO roomCreReqDTO,
                                    BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Long specialityId = Long.valueOf(roomCreReqDTO.getSpecialityId());
        Speciality speciality = specialityService.findById(specialityId).orElseThrow(()->{
            throw new DataInputException("Mã khoa không tồn tại");
        });

        Room room = roomCreReqDTO.toRoom(speciality);
        Room newRoom = roomService.save(room);
        RoomResDTO roomResDTO = newRoom.toRoomResDTO();

        return new ResponseEntity<>(roomResDTO, HttpStatus.CREATED);
    }
}
