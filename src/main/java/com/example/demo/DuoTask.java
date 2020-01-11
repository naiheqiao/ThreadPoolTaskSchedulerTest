package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qly
 * @date 2020/1/11
 * @description 初始化加载所有动态任务
 */
@Component
public class DuoTask {

    private TestController tc;

    public DuoTask(TestController tc) {
        this.tc = tc;
        tc.startCron();
    }
}