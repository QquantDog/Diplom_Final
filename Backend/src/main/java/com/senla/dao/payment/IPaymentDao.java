package com.senla.dao.payment;

import com.senla.dto.pagination.PaginationDetails;
import com.senla.model.payment.Payment;
import com.senla.util.dao.GenericDao;

import java.util.List;


public interface IPaymentDao extends GenericDao<Payment, Long> {
    List<Payment> getPaymentsByCustomerId(Long customerId, PaginationDetails paginationDetails);
    List<Payment> findAllWithRides(PaginationDetails paginationDetails);
    List<Payment> getPaymentsByDriverId(Long driverId, PaginationDetails paginationDetails);
}
