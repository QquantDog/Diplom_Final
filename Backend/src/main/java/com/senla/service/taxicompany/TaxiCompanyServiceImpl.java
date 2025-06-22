package com.senla.service.taxicompany;

import com.senla.dao.taxicompany.TaxiCompanyDao;
import com.senla.dto.pagination.PaginationRequest;
import com.senla.dto.taxicompany.TaxiCompanyCreateDto;
import com.senla.dto.taxicompany.TaxiCompanyFilterDto;
import com.senla.dto.taxicompany.TaxiCompanyUpdateDto;
import com.senla.exception.DaoException;
import com.senla.exception.PaginationException;
import com.senla.model.taxicompany.TaxiCompany;
import com.senla.specification.TaxiCompanySpecification;
import com.senla.util.pagination.PaginationUtils;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class TaxiCompanyServiceImpl extends AbstractLongIdGenericService<TaxiCompany> implements TaxiCompanyService {

    @Autowired
    private TaxiCompanyDao taxiCompanyDao;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @PostConstruct
    protected void init() {
        super.abstractDao = taxiCompanyDao;
    }

    @Override
    @Transactional
    public TaxiCompany createTaxiCompany(TaxiCompanyCreateDto dto) {
        TaxiCompany taxiCompany = modelMapper.map(dto, TaxiCompany.class);
        return abstractDao.create(taxiCompany);
    }

    @Override
    @Transactional
    public TaxiCompany updateTaxiCompany(Long id, TaxiCompanyUpdateDto dto) {
        Optional<TaxiCompany> resp = abstractDao.findById(id);
        if (resp.isEmpty()) throw new DaoException("Can't find entity with id " + id);

        TaxiCompany taxiCompanyToUpdate =  resp.get();
        modelMapper.map(dto, taxiCompanyToUpdate);
        return abstractDao.update(taxiCompanyToUpdate);
    }

    @Override
    public List<TaxiCompany> getWithCabList() {
        return taxiCompanyDao.getAllWithCabs();
    }

    @Override
    @Transactional
    public TaxiCompany getCompanyWithCabs(Long companyId) {
        return taxiCompanyDao.getCompanyWithCabs(companyId)
                .orElseThrow(()->new DaoException("Can't find company with id " + companyId));
    }

    @Override
    public List<TaxiCompany> findAllWithFilterAndPagination(TaxiCompanyFilterDto filterDto, PaginationRequest paginationRequest) {
        return taxiCompanyDao.getAllWithFilterAndPagination(TaxiCompanySpecification.buildSpecification(filterDto), PaginationUtils
                .getOffsetByCountAndParams((int) taxiCompanyDao.count(), paginationRequest)
                    .orElseThrow(PaginationException::new));
    }
}
