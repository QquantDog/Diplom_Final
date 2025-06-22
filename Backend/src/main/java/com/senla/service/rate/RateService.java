package com.senla.service.rate;

import com.senla.dto.pagination.PaginationRequest;
import com.senla.dto.rate.RateCreateDto;
import com.senla.dto.rate.RateFilterDto;
import com.senla.dto.rate.RateUpdateDto;
import com.senla.model.rate.Rate;
import com.senla.util.service.GenericService;

import java.util.List;
import java.util.Optional;

public interface RateService extends GenericService<Rate, Long> {
    Rate createRate(RateCreateDto dto);
    Rate updateRate(Long id, RateUpdateDto dto);
    List<Rate> getAll();
    Optional<Rate> getById(Long id);
    List<Rate> getAllWithFilterAndPagination(RateFilterDto rateFilterDto, PaginationRequest paginationRequest);
}
