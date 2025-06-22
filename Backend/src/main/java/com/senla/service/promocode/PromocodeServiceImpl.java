package com.senla.service.promocode;

import com.senla.dao.promocode.PromocodeDao;
import com.senla.dto.pagination.PaginationRequest;
import com.senla.dto.promocode.PromocodeFilterDto;
import com.senla.dto.promocode.PromocodeUpdateDto;
import com.senla.exception.DaoException;
import com.senla.exception.PaginationException;
import com.senla.model.promocode.Promocode;
import com.senla.specification.PromocodeSpecification;
import com.senla.util.pagination.PaginationUtils;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PromocodeServiceImpl extends AbstractLongIdGenericService<Promocode> implements PromocodeService {

    @Autowired
    private PromocodeDao promocodeDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @PostConstruct
    protected void init() {
        this.abstractDao = promocodeDao;
    }

    @Override
    @Transactional
    public Promocode updatePromocode(Long id, PromocodeUpdateDto promocodeUpdateDto) {
        Optional<Promocode> resp = abstractDao.findById(id);
        if (resp.isEmpty()) throw new DaoException("Can't find entity with id " + id);
        else {
            Promocode promocode = resp.get();
            modelMapper.map(promocodeUpdateDto, promocode);
            abstractDao.update(promocode);
            return promocode;
        }
    }

    @Override
    public List<Promocode> findPromocodesBySpecification(PromocodeFilterDto filterDto) {
        return promocodeDao.findBySpecification(PromocodeSpecification.buildSpecification(filterDto));
    }

    @Override
    public List<Promocode> findAllWithPagination(PaginationRequest paginationRequest) {
        return promocodeDao.findAllWithOffsetAndLimit(PaginationUtils
                .getOffsetByCountAndParams((int) promocodeDao.count(), paginationRequest)
                    .orElseThrow(PaginationException::new));
    }

}
