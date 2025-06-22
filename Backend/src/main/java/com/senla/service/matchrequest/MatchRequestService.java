package com.senla.service.matchrequest;

import com.senla.dto.pagination.PaginationRequest;
import com.senla.model.matchrequest.MatchRequest;
import com.senla.util.service.GenericService;

import java.util.List;

public interface MatchRequestService extends GenericService<MatchRequest, Long> {
    List<MatchRequest> findAllMatches(PaginationRequest paginationRequest);
    void deactivateRequest(Long requestId);
    void deactivateAllNotActiveRequestsForTimeout();
}
