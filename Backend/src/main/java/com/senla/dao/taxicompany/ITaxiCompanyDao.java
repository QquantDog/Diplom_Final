package com.senla.dao.taxicompany;

import com.senla.dto.pagination.PaginationDetails;
import com.senla.model.taxicompany.TaxiCompany;
import com.senla.util.dao.GenericDao;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface ITaxiCompanyDao extends GenericDao<TaxiCompany, Long>  {
    List<TaxiCompany> getAllWithCabs();
    Optional<TaxiCompany> getCompanyWithCabs(Long companyId);
    List<TaxiCompany> getAllWithFilterAndPagination(Specification<TaxiCompany> specification, PaginationDetails paginationDetails);
}
