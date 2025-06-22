package com.senla.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.dto.pagination.PaginationRequest;
import com.senla.dto.promocode.PromocodeCreateDto;
import com.senla.dto.promocode.PromocodeFilterDto;
import com.senla.dto.promocode.PromocodeResponseDto;
import com.senla.dto.promocode.PromocodeUpdateDto;
import com.senla.exception.DaoException;
import com.senla.exception.NotFoundByIdException;
import com.senla.model.promocode.Promocode;
import com.senla.model.user.User;
import com.senla.service.promocode.PromocodeService;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/promocodes")
@RequiredArgsConstructor
public class PromocodeController {
    private final PromocodeService promocodeService;
    private final ModelMapper modelMapper;

    @Autowired
    public PromocodeController(PromocodeService promocodeService, ObjectMapper jsonMapper, ModelMapper modelMapper) {
        this.promocodeService = promocodeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('PROMO_READ_ALL')")
    public ResponseEntity<List<PromocodeResponseDto>> getAllPromocodes(@Valid PaginationRequest paginationRequest) {
        List<Promocode> promocodes = this.promocodeService.findAllWithPagination(paginationRequest);
        return new ResponseEntity<>(promocodes.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('PROMO_READ_ALL')")
    public ResponseEntity<List<PromocodeResponseDto>> getAllPromocodesWithSearch(@Valid PromocodeFilterDto filterDto) {
        List<Promocode> promocodes = this.promocodeService.findPromocodesBySpecification(filterDto);
        return new ResponseEntity<>(promocodes.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PROMO_CRUD')")
    public ResponseEntity<PromocodeResponseDto> getPromocodeById(@PathVariable("id") Long id) throws JsonProcessingException, NotFoundByIdException {
        Optional<Promocode> promocode = this.promocodeService.findById(id);
        if(promocode.isPresent()){
            return new ResponseEntity<>(convertToResponseDto(promocode.get()), HttpStatus.OK);
        } else{
            throw new NotFoundByIdException(User.class);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PROMO_CRUD')")
    public ResponseEntity<PromocodeResponseDto> createPromocode(@Valid @RequestBody PromocodeCreateDto promocodeCreateDto, BindingResult bindingResult) throws JsonProcessingException, DaoException, NoSuchMethodException, MethodArgumentNotValidException {
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            throw new MethodArgumentNotValidException(new MethodParameter(this.getClass().getMethod("createPromocode", PromocodeCreateDto.class, BindingResult.class), 0), bindingResult);
        }
        Promocode resp = promocodeService.create(modelMapper.map(promocodeCreateDto, Promocode.class));
        return new ResponseEntity<>(modelMapper.map(resp, PromocodeResponseDto.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PROMO_CRUD')")
    public ResponseEntity<PromocodeResponseDto> updatePromocode(@Valid @RequestBody PromocodeUpdateDto promocodeUpdateDto, BindingResult bindingResult, @PathVariable("id") Long id) throws JsonProcessingException, NoSuchMethodException, MethodArgumentNotValidException {
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            throw new MethodArgumentNotValidException(new MethodParameter(this.getClass().getMethod("updatePromocode", PromocodeUpdateDto.class, BindingResult.class, Long.class), 0), bindingResult);
        }
        Promocode resp = promocodeService.updatePromocode(id, promocodeUpdateDto);
        return new ResponseEntity<>(modelMapper.map(resp, PromocodeResponseDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PROMO_CRUD')")
    public ResponseEntity<?> deletePromocode(@PathVariable("id") Long id) {
        promocodeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private PromocodeResponseDto convertToResponseDto(Promocode promocode) {
        return modelMapper.map(promocode, PromocodeResponseDto.class);
    }
}