package com.senla.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.dto.customerrating.CustomerRatingCreateDto;
import com.senla.dto.customerrating.CustomerRatingResponseDto;
import com.senla.dto.customerrating.CustomerRatingUpdateDto;
import com.senla.dto.pagination.PaginationRequest;
import com.senla.exception.NotFoundByIdException;
import com.senla.model.customerrating.CustomerRating;
import com.senla.service.customerrating.CustomerRatingService;
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
@RequestMapping("/customer-ratings")
@RequiredArgsConstructor
public class CustomerRatingController {

    private final CustomerRatingService customerRatingService;
    private final ModelMapper modelMapper;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('RATING_READ_ALL')")
    public ResponseEntity<List<CustomerRatingResponseDto>> getAllCustomerRatings(@Valid PaginationRequest paginationRequest) throws JsonProcessingException {
        List<CustomerRating> ratings = customerRatingService.findAllRatingsWithRides(paginationRequest);
        return new ResponseEntity<>(ratings.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{ratingId}")
    @PreAuthorize("hasAuthority('RATING_READ_ALL')")
    public ResponseEntity<CustomerRatingResponseDto> getCustomerRatingId(@PathVariable("ratingId") Long ratingId) throws JsonProcessingException, NotFoundByIdException {
        CustomerRating customerRating = customerRatingService.findRatingByIdWithRides(ratingId);
        return new ResponseEntity<>(convertToResponseDto(customerRating), HttpStatus.OK);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('RATING_READ_OWN')")
    public ResponseEntity<List<CustomerRatingResponseDto>> getMyRatings(@Valid PaginationRequest paginationRequest) throws JsonProcessingException {
        List<CustomerRating> ratings = customerRatingService.findMyRatingsWithRides(paginationRequest);
        return new ResponseEntity<>(ratings.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/my/{ratingId}")
    @PreAuthorize("hasAuthority('RATING_READ_OWN')")
    public ResponseEntity<CustomerRatingResponseDto> getMyRatingId(@PathVariable("ratingId") Long ratingId) throws JsonProcessingException, NotFoundByIdException {
        CustomerRating customerRating = customerRatingService.findMyRatingByIdWithRides(ratingId);
        return new ResponseEntity<>(convertToResponseDto(customerRating), HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('RATING_PROCESS_CUSTOMER')")
    public ResponseEntity<CustomerRatingResponseDto> rateDriver(@Valid @RequestBody CustomerRatingCreateDto customerRatingCreateDto) throws JsonProcessingException {
        CustomerRating customerRating = customerRatingService.createCustomerRate(customerRatingCreateDto);
        return new ResponseEntity<>(convertToResponseDto(customerRating), HttpStatus.CREATED);
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('RATING_PROCESS_CUSTOMER')")
    public ResponseEntity<CustomerRatingResponseDto> updateCustomerRating(@Valid @RequestBody CustomerRatingUpdateDto customerRatingUpdateDto) throws JsonProcessingException {
        return new ResponseEntity<>(convertToResponseDto(customerRatingService.updateCustomerRate(customerRatingUpdateDto)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{ratingId}")
    @PreAuthorize("hasAuthority('RATING_DEL_ADMIN')")
    public ResponseEntity<?> deleteCustomerRating(@PathVariable("ratingId") Long ratingId) {
        customerRatingService.deleteById(ratingId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/my/{ratingId}")
    @PreAuthorize("hasAuthority('RATING_PROCESS_CUSTOMER')")
    public ResponseEntity<?> deleteMyRating(@PathVariable("ratingId") Long ratingId) {
        customerRatingService.deleteMyRatingById(ratingId);
        return ResponseEntity.noContent().build();
    }

    private CustomerRatingResponseDto convertToResponseDto(CustomerRating customerRating) {
        return modelMapper.map(customerRating, CustomerRatingResponseDto.class);
    }
}
