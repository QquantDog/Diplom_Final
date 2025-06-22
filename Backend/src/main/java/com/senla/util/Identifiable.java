package com.senla.util;

public interface Identifiable<ID> {
    ID getId();
    void setId(ID id);
}