package com.example.demo;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledFuture;

/**
 * @program: demo
 * @description: mytask
 * @author: duxiangyu
 * @create: 2019-06-12 17:10
 */
public class MyTask implements Runnable {

    private String departmentName;

    private String cronStr;

    private ScheduledFuture<?> future;

    public String getCronStr() {
        return cronStr;
    }

    public void setCronStr(String cronStr) {
        this.cronStr = cronStr;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public MyTask(String departmentName,String cronStr) {
        this.departmentName = departmentName;
        this.cronStr=cronStr;
    }

    public ScheduledFuture<?> getFuture() {
        return future;
    }

    public void setFuture(ScheduledFuture<?> future) {
        this.future = future;
    }

    @Override
    public  void run() {
        int i = (int) (1 + Math.random() * 10);
        System.out.println( getDepartmentName() + ":小" + i + "   获取线索"+ LocalDateTime.now().toString());
    }
}