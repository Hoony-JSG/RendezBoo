package com.ssafy.a107.db.entity;

import java.io.Serializable;

public class UserItemId implements Serializable {

    private Long user;
    private Long item;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserItemId that = (UserItemId) o;

        if (!user.equals(that.user)) return false;
        return item.equals(that.item);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + item.hashCode();
        return result;
    }
}
