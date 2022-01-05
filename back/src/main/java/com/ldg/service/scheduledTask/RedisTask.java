package com.ldg.service.scheduledTask;

import com.ldg.service.impl.DynamicServiceImpl;
import com.ldg.utils.DynamicRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author Administrator
 */
@Configuration
@EnableScheduling
@EnableAsync
public class RedisTask {

    @Autowired
    private DynamicRedis dynamicRedis;

    @Autowired
    private DynamicServiceImpl dynamicService;

    /**线程池*/
    private ThreadPoolExecutor threadPool;
    /**cpu的数量*/
    private int CPU_NUM;

    /**
     * 使用给定的初始参数创建新的ThreadPoolExecutor。
     * 参数：
     * corePoolSize–池中要保留的线程数，即使它们处于空闲状态，除非设置了allowCoreThreadTimeOut
     * maximumPoolSize–池中允许的最大线程数
     * keepAliveTime–当线程数大于核心时，这是多余空闲线程在终止前等待新任务的最长时间。
     * unit–keepAliveTime参数的时间单位
     * 工作队列–用于在任务执行前保存任务的队列。此队列将仅包含execute方法提交的可运行任务。
     * threadFactory–执行器创建新线程时使用的工厂
     * handler–由于达到线程边界和队列容量而阻止执行时要使用的处理程序
     * 抛出：
     * IllegalArgumentException–如果以下情况之一成立：corePoolSize<0 keepAliveTime<0 maximumPoolSize<=0 maximumPoolSize<corePoolSize
     * NullPointerException–如果工作队列、线程工厂或处理程序为null
     *  public ThreadPoolExecutor(int corePoolSize,
     *                               int maximumPoolSize,
     *                               long keepAliveTime,
     *                               TimeUnit unit,
     *                               BlockingQueue<Runnable> workQueue,
     *                               ThreadFactory threadFactory,
     *                               RejectedExecutionHandler handler)
     *                               Creates a new ThreadPoolExecutor with the given initial parameters.
     */

    RedisTask(){

        this.CPU_NUM=Runtime.getRuntime().availableProcessors();

        threadPool=new ThreadPoolExecutor(CPU_NUM,
                CPU_NUM*2,
                10, TimeUnit.MINUTES,
                new ArrayBlockingQueue<Runnable>(16),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }




    // @Scheduled(cron = "0/5 * * * * ?")
    public void updateDynamicZan(){
        Map<Object, Object> allMap = dynamicRedis.getAllMap();
        Iterator<Map.Entry<Object, Object>> iterator = allMap.entrySet().iterator();
        HashSet<Integer> set=new HashSet<>();
        set.iterator();
        int count=allMap.size();


    }



    public void updateSql(){

    }


    class UpdateDynamic implements Runnable{

        UpdateDynamic(){

        }

        @Override
        public void run() {

        }
    }





}
