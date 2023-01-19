package com.ssafy.a107.db.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserInterestSeq implements Serializable {

    private String member;
    private String product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInterestSeq that = (UserInterestSeq) o;
        return Objects.equals(member, that.member) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, product);
    }
}
