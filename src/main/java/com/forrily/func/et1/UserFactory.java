package com.forrily.func.et1;

public interface UserFactory<U extends User> {
    U creat(int id, String name);
}
