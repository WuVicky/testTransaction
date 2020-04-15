package com.transaction.test.service.impl;

import com.transaction.test.dao.UserMapper;
import com.transaction.test.entry.User;
import com.transaction.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private com.transaction.test.dao.UserMapper UserMapper;

    public void truncate(){
        UserMapper.truncated();
    }

    @Override
    public void add(User user){
        UserMapper.insert(user);
    }

    @Override
    public void addException(User user){
        UserMapper.insert(user);
        throw new RuntimeException();
    }

    /* (non-Javadoc)
     * @see org.transaction.test.local_transaction.mybatis.service.impl.UserService#add(org.transaction.test.global_transaction.mybatis.bean.User)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRequired(User user){
        UserMapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRequiredException(User user){
        UserMapper.insert(user);
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void addSupports(User user){
        UserMapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void addSupportsException(User user){
        UserMapper.insert(user);
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRequiresNew(User user){
        UserMapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRequiresNewException(User user){
        UserMapper.insert(user);
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void addNotSupported(User user){
        UserMapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void addNotSupportedException(User user){
        UserMapper.insert(user);
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addMandatory(User user){
        UserMapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addMandatoryException(User user){
        UserMapper.insert(user);
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void addNever(User user){
        UserMapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void addNeverException(User user){
        UserMapper.insert(user);
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void addNested(User user){
        UserMapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void addNestedException(User user){
        UserMapper.insert(user);
        throw new RuntimeException();
    }

}
