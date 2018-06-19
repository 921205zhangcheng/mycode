package com.xh.sdk.control;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;

import com.xh.sdk.redis.RedisDao;

@Controller

public class DomainRedisController implements ApplicationListener<ContextRefreshedEvent>{


@Autowired
private RedisDao rd;
@Override
public void onApplicationEvent(ContextRefreshedEvent arg0) {

// TODO Auto-generated method stub

System.out.println("all is run");

//rd.synallToRedis();




}
}