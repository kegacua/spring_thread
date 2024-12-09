package com.example.spring_thread.service;

import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.spring_thread.entity.User;
import com.example.spring_thread.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final ReentrantLock lock = new ReentrantLock();

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void addUser(String name, Long money){
        lock.lock();
        try{
            User user = new User();
            user.setName(name);
            user.setMoney(money);
            userRepository.save(user);
            logger.info("them user: " , name);
        } finally{
            lock.unlock();
        }
    }
    public void updateUser(int id, String newName, Long money){
        lock.lock();
        try{
            User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("khong tim thay user"));
            user.setName(newName);
            user.setMoney(money);
            userRepository.save(user);
            logger.info("cap nhat user voi id",id);
        } finally{
            lock.unlock();
        }
    }
    public void deleteUser(int id){
        lock.lock();
        try{
            userRepository.deleteById(id);
            logger.info("xoa thanh cong user id",id);
        } finally{
            lock.unlock();
        }
    }

}
