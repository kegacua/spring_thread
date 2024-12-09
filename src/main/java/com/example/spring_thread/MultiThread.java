package com.example.spring_thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.spring_thread.service.UserService;

@Component
public class MultiThread implements CommandLineRunner{
    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
       
        List<Future<?>> futures = new ArrayList<>();

       
        for (int i = 0; i < 10; i++) {
            final int iTask = i;
            futures.add(executorService.submit(() -> {
                try {
                   
                    if (iTask % 3 == 0) {
                        userService.addUser("User"+iTask,(long)30+iTask*2);
                        System.out.println("Task " + iTask + " started by " + Thread.currentThread().getName());
                    }
                    // Thực hiện thao tác sửa dữ liệu
                    else if (iTask % 3 == 1) {
                        userService.updateUser(iTask,"User"+iTask*2,(long)30+iTask*3);
                        System.out.println("Task " + iTask + " started by " + Thread.currentThread().getName());
                    }
                    // Thực hiện thao tác xóa dữ liệu
                    else {
                        userService.deleteUser(iTask);
                        System.out.println("Task " + iTask + " started by " + Thread.currentThread().getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Task " + iTask + " finish by " + Thread.currentThread().getName());
            }));
        }

        // Chờ tất cả các luồng thực hiện xong
        for (Future<?> future : futures) {
            future.get();  // Đợi cho đến khi mỗi luồng hoàn thành
        }

        executorService.shutdown();
        
    }



}
