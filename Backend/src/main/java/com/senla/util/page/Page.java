package com.senla.util.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Page<T> {
    private T data;
    private int total;
    private int page;
    private int limit;
}
