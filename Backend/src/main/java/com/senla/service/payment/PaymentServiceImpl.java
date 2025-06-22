package com.senla.service.payment;

import com.senla.dao.payment.PaymentDao;
import com.senla.dto.pagination.PaginationRequest;
import com.senla.exception.PaginationException;
import com.senla.model.payment.Payment;
import com.senla.util.pagination.PaginationUtils;
import com.senla.util.sec.SecUtils;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentServiceImpl extends AbstractLongIdGenericService<Payment> implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;


    @PostConstruct
    @Override
    public void init() {
        super.abstractDao = paymentDao;
    }


    @Override
    public List<Payment> getAllByCustomerId(PaginationRequest paginationRequest) {
        return paymentDao.getPaymentsByCustomerId(SecUtils.extractId(), PaginationUtils
                .getOffsetByCountAndParams((int) paymentDao.count(), paginationRequest)
                    .orElseThrow(PaginationException::new));
    }

    @Override
    public List<Payment> getAllByDriverId(PaginationRequest paginationRequest) {
        return paymentDao.getPaymentsByDriverId(SecUtils.extractId(), PaginationUtils
                .getOffsetByCountAndParams((int) paymentDao.count(), paginationRequest)
                    .orElseThrow(PaginationException::new));
    }

    @Override
    public List<Payment> findAllWithRides(PaginationRequest paginationRequest) {
        return paymentDao.findAllWithRides(PaginationUtils
                .getOffsetByCountAndParams((int) paymentDao.count(), paginationRequest)
                .orElseThrow(PaginationException::new));
    }

}
