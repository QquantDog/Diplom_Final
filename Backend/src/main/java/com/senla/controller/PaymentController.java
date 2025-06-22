package com.senla.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.dto.pagination.PaginationRequest;
import com.senla.dto.payment.PaymentResponseDto;
import com.senla.dto.payment.PaymentResponseWithRideDto;
import com.senla.model.payment.Payment;
import com.senla.service.payment.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final ModelMapper modelMapper;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority({'PAYMENT_READ_ALL'})")
    public ResponseEntity<List<PaymentResponseWithRideDto>> getAllPayments(@Valid PaginationRequest paginationRequest) throws JsonProcessingException {
        List<Payment> payments = paymentService.findAllWithRides(paginationRequest);
        return new ResponseEntity<>(payments.stream().map(this::convertToResponseWithRideDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/customer")
    @PreAuthorize("hasAuthority({'PAYMENT_READ_CUSTOMER'})")
    public ResponseEntity<List<PaymentResponseWithRideDto>> getCustomerPayments(@Valid PaginationRequest paginationRequest) throws JsonProcessingException {
        List<Payment> payments = paymentService.getAllByCustomerId(paginationRequest);
        return new ResponseEntity<>(payments.stream().map(this::convertToResponseWithRideDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/driver")
    @PreAuthorize("hasAuthority({'PAYMENT_READ_DRIVER'})")
    public ResponseEntity<List<PaymentResponseWithRideDto>> getDriverPayments(@Valid PaginationRequest paginationRequest) throws JsonProcessingException {
        List<Payment> payments = paymentService.getAllByDriverId(paginationRequest);
        return new ResponseEntity<>(payments.stream().map(this::convertToResponseWithRideDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    private PaymentResponseDto convertToResponseDto(Payment payment){
        return modelMapper.map(payment, PaymentResponseDto.class);
    }
    private PaymentResponseWithRideDto convertToResponseWithRideDto(Payment payment){
        return modelMapper.map(payment, PaymentResponseWithRideDto.class);
    }
}
