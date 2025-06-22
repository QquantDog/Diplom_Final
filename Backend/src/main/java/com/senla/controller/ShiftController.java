package com.senla.controller;

import com.senla.dto.shift.ShiftFullResponseDto;
import com.senla.dto.shift.ShiftResponseDto;
import com.senla.dto.shift.ShiftStartDto;
import com.senla.model.shift.Shift;
import com.senla.service.shift.ShiftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shifts")
@RequiredArgsConstructor
public class ShiftController {

    private final ShiftService shiftService;
    private final ModelMapper modelMapper;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority({'SHIFT_GET_ALL'})")
    public ResponseEntity<List<ShiftFullResponseDto>> getAllShifts() {
        List<Shift> shifts = shiftService.getAllShiftsFullResponse();
        return new ResponseEntity<>(shifts.stream().map(this::convertToFullResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority({'SHIFT_GET_OWN'})")
    public ResponseEntity<List<ShiftFullResponseDto>> getAllDriverShifts() {
        List<Shift> shifts = shiftService.getDriverShiftsFullResponse();
        return new ResponseEntity<>(shifts.stream().map(this::convertToFullResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/my/active")
    @PreAuthorize("hasAuthority({'SHIFT_GET_OWN'})")
    public ResponseEntity<ShiftResponseDto> getActiveDriverShift() {
        Shift shift = shiftService.getActiveShift();
        return new ResponseEntity<>(convertToResponseDto(shift), HttpStatus.OK);
    }

    @PostMapping("/start")
    @PreAuthorize("hasAuthority({'SHIFT_PROCESS'})")
    public ResponseEntity<ShiftFullResponseDto> startShift(@RequestBody @Valid ShiftStartDto shiftStartDto)  {
        Shift shift = shiftService.startShift(shiftStartDto);
        return new ResponseEntity<>(convertToFullResponseDto(shift), HttpStatus.CREATED);
    }

    @PostMapping("/finish")
    @PreAuthorize("hasAuthority({'SHIFT_PROCESS'})")
    public ResponseEntity<ShiftFullResponseDto> finishShift() {
        Shift shift = shiftService.finishShift();
        return new ResponseEntity<>(convertToFullResponseDto(shift), HttpStatus.CREATED);
    }

    private ShiftResponseDto convertToResponseDto(Shift shift){
        return modelMapper.map(shift, ShiftResponseDto.class);
    }

    private ShiftFullResponseDto convertToFullResponseDto(Shift shift){
        return modelMapper.map(shift, ShiftFullResponseDto.class);
    }
}
