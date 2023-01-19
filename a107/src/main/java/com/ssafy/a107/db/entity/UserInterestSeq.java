package com.ssafy.a107.db.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserInterestSeq implements Serializable {

    private Long user;
    private Long interest;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInterestSeq that = (UserInterestSeq) o;
        return Objects.equals(user, that.user) && Objects.equals(interest, that.interest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, interest);
    }
}
