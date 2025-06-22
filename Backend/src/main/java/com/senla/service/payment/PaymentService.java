package com.senla.service.payment;

import com.senla.dto.pagination.PaginationRequest;
import com.senla.model.payment.Payment;
import com.senla.util.service.GenericService;

import java.util.List;

public interface PaymentService extends GenericService<Payment, Long> {
    List<Payment> getAllByCustomerId(PaginationRequest paginationRequest);
    List<Payment> getAllByDriverId(PaginationRequest paginationRequest);
    List<Payment> findAllWithRides(PaginationRequest paginationRequest);
}
