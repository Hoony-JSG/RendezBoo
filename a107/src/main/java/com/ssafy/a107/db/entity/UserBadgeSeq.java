package com.ssafy.a107.db.entity;

import java.io.Serializable;

public class UserBadgeSeq implements Serializable {

    private Long user;
    private Long badge;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserBadgeSeq that = (UserBadgeSeq) o;

        if (!user.equals(that.user)) return false;
        return badge.equals(that.badge);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + badge.hashCode();
        return result;
    }
}
