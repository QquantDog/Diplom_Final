package com.senla.service.taxicompany;

import com.senla.dto.pagination.PaginationRequest;
import com.senla.dto.taxicompany.TaxiCompanyCreateDto;
import com.senla.dto.taxicompany.TaxiCompanyFilterDto;
import com.senla.dto.taxicompany.TaxiCompanyUpdateDto;
import com.senla.model.taxicompany.TaxiCompany;
import com.senla.util.service.GenericService;

import java.util.List;


public interface TaxiCompanyService extends GenericService<TaxiCompany, Long> {
    TaxiCompany createTaxiCompany(TaxiCompanyCreateDto taxiCompanyCreateDto);
    TaxiCompany updateTaxiCompany(Long id, TaxiCompanyUpdateDto taxiCompanyUpdateDto);
    List<TaxiCompany> getWithCabList();
    TaxiCompany getCompanyWithCabs(Long companyId);

    List<TaxiCompany> findAllWithFilterAndPagination(TaxiCompanyFilterDto filterDto, PaginationRequest paginationRequest);
}
