package com.senla.util.pagination;

import com.senla.dto.pagination.PaginationDetails;
import com.senla.dto.pagination.PaginationRequest;

import java.util.Optional;

public class PaginationUtils {

    public static Optional<PaginationDetails> getOffsetByCountAndParams(int count, PaginationRequest paginationRequest) {
        int limit = paginationRequest.getLimit();
        int page = paginationRequest.getPage();

        if(count <= (page-1)*limit ) return Optional.empty();
        else return Optional.of(PaginationDetails.builder()
                .offset((page-1)*limit)
                .limit(limit)
                .build());
    }

    public static PaginationRequest buildPaginationRequest(int limit, int page){
        if(limit <= 0) {
            throw new IllegalArgumentException("Limit must be greater than 0");
        }
        if(page <= 0) {
            throw new IllegalArgumentException("Page must be greater than 0");
        }
        return new PaginationRequest(limit, page);
    }
}
