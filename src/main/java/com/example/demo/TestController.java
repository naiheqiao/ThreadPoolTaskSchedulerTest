package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: demo
 * @description: test
 * @author: duxiangyu
 * @create: 2019-06-12 16:46
 */
@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(4);
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return  threadPoolTaskScheduler;
    }
   /**
    * 功能描述: TODO:根据需求场景设置任务类模型
    */
    private List<MyTask> myTaskList;


    @RequestMapping("home")
    @ResponseBody
    public String home(){
        return "home";
    }
    @RequestMapping("/startCron")
    @ResponseBody
    public void startCron(){
        myTaskList=new ArrayList<>();
        myTaskList.add(new MyTask("天地一部","*/2 * * * * *"));
//        myTaskList.add(new MyTask("天地二部","*/3 * * * * *"));
//        myTaskList.add(new MyTask("王者一部","*/5 * * * * *"));
        myTaskList.add(new MyTask("王者二部","*/7 * * * * *"));
//        myTaskList.add(new MyTask("四方军团","*/11 * * * * *"));

        myTaskList.forEach(o->{
            ScheduledFuture future = threadPoolTaskScheduler.schedule(o,new CronTrigger(o.getCronStr()));
            o.setFuture(future);
        });
    }
    /**
     * 功能描述: TODO:根据需求修改停止任务的方法 该事例用tasklist的index来停止
     */
    @RequestMapping("/stopCron")
    @ResponseBody
    public String stopCron(@RequestParam("index") Integer index){
        System.out.println("stop >>>>>");
        if(myTaskList==null||myTaskList.size()<(index+1)){
            return "stop cron:    "+index +"失败 任务不存在";
        }

        ScheduledFuture future=myTaskList.get(index).getFuture();
        if(future != null) {
            future.cancel(true);
        }
        else {
            return "stop cron:    "+index +"失败 任务不存在";
        }
        System.out.println("stop <<<<<");
        return "stop cron:    "+index;
    }
    //TODO:根据需求修改修改任务的方法 该事例用tasklist的index来修改
    /**
     * 功能描述: TODO:根据需求修改停止任务的方法 该事例用tasklist的index来停止
     */
    @RequestMapping("/updateCron")
    @ResponseBody
    public String updateCron(@RequestParam("index") Integer index,@RequestParam("cronStr") String cronStr){
        System.out.println("update >>>>>");
        if(myTaskList==null||myTaskList.size()<(index+1)){
            return "update cron:    "+index +"失败 任务不存在";
        }

        ScheduledFuture future=myTaskList.get(index).getFuture();
        if(future != null) {
            future.cancel(true);
        }
        else {
            return "update cron:    "+index +"失败 任务不存在";
        }
        myTaskList.get(index).setCronStr(cronStr);
        future= threadPoolTaskScheduler.schedule(myTaskList.get(index), new CronTrigger(myTaskList.get(index).getCronStr()));
        myTaskList.get(index).setFuture(future);
        System.out.println("update <<<<<");
        return "update cron:    "+index;
    }
}