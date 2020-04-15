package com.transaction.test.service.impl;

import com.transaction.test.dao.User1Mapper;
import com.transaction.test.entry.User1;
import com.transaction.test.service.User1Service;
import com.transaction.test.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class User1ServiceImpl implements User1Service {
    @Resource
    private User1Mapper user1Mapper;

    public void truncate(){
        user1Mapper.truncated();
    }

    @Override
    public void add(User1 user){
        user1Mapper.insert(user);
    }

    @Override
    public void addException(User1 user){
        user1Mapper.insert(user);
        throw new RuntimeException();
    }

    /* (non-Javadoc)
     * @see org.transaction.test.local_transaction.mybatis.service.impl.User1Service#add(org.transaction.test.global_transaction.mybatis.bean.User1)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRequired(User1 user){
        user1Mapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRequiredException(User1 user){
        user1Mapper.insert(user);
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void addSupports(User1 user){
        user1Mapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void addSupportsException(User1 user){
        user1Mapper.insert(user);
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRequiresNew(User1 user){
        user1Mapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRequiresNewException(User1 user){
        user1Mapper.insert(user);
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void addNotSupported(User1 user){
        user1Mapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void addNotSupportedException(User1 user){
        user1Mapper.insert(user);
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addMandatory(User1 user){
        user1Mapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addMandatoryException(User1 user){
        user1Mapper.insert(user);
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void addNever(User1 user){
        user1Mapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void addNeverException(User1 user){
        user1Mapper.insert(user);
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void addNested(User1 user){
        user1Mapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void addNestedException(User1 user){
        user1Mapper.insert(user);
        throw new RuntimeException();
    }
}
