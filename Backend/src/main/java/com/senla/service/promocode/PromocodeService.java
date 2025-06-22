package com.senla.service.promocode;

import com.senla.dto.pagination.PaginationRequest;
import com.senla.dto.promocode.PromocodeFilterDto;
import com.senla.dto.promocode.PromocodeUpdateDto;
import com.senla.model.promocode.Promocode;
import com.senla.util.service.GenericService;

import java.util.List;

public interface PromocodeService extends GenericService<Promocode, Long> {
    Promocode updatePromocode(Long id, PromocodeUpdateDto promocodeUpdateDto);
    List<Promocode> findPromocodesBySpecification(PromocodeFilterDto filterDto);
    List<Promocode> findAllWithPagination(PaginationRequest paginationRequest);
}
