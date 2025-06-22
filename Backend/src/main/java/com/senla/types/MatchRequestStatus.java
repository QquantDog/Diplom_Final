package com.senla.types;

import lombok.Getter;

@Getter
public enum MatchRequestStatus {
    OFFERED,
    DECLINED,
    ACCEPTED,
    STALE;
}
