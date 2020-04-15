package com.transaction.test.service;

import com.transaction.test.entry.User;
import org.springframework.stereotype.Component;


public interface UserService {
    void addRequired(User user);

    void addRequiredException(User user);

    void truncate();

    void addSupports(User user);

    void addSupportsException(User user);

    void addRequiresNew(User user);

    void addRequiresNewException(User user);

    void addNotSupported(User user);

    void addNotSupportedException(User user);

    void add(User user);

    void addException(User user);

    void addMandatory(User user);

    void addMandatoryException(User user);

    void addNever(User user);

    void addNeverException(User user);

    void addNested(User user);

    void addNestedException(User user);
}
