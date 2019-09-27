package com.miracle.library.manager;

import android.content.Context;
import com.miracle.library.RequestBuilder;
import com.miracle.library.task.DispatcherTask;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author miracle
 * @date 2019-09-25
 * @email ruanyandongai@gmail.com
 * @blog https://ruanyandong.github.io
 */
public class RequestManager {

    private static RequestManager instance;
    /**
     * 上下文
     */
    private Context context;
    /**
     * 加载任务器
     */
    private DispatcherTask[] tasks;
    /**
     * 创建一个队列,单向队列，头部获取数据，尾部新增数据，并发时保证线程安全
     */
    private LinkedBlockingQueue<RequestBuilder> requestQueue = new LinkedBlockingQueue<>();

    public static RequestManager getInstance() {
        if (instance == null){
            synchronized (RequestManager.class){
                if (instance == null){
                    instance = new RequestManager();
                }
            }
        }
        return instance;
    }

    private RequestManager(){
    }

    public RequestManager get(Context context) {
        this.context = context;
        return this;
    }

    public RequestBuilder load(String url){
        return new RequestBuilder(context).load(url);
    }
    /**
     * 需要加载的请求对象，放入队列中
     * @param requestBuilder
     */
    public RequestManager addRequestQueue(RequestBuilder requestBuilder) {
        // 有没有重复的请求对象
        if (!requestQueue.contains(requestBuilder)){
            requestQueue.add(requestBuilder);
        }
        return this;
    }

    /**
     * 触发操作
     */
    public void dispatcher() {
        // 获取虚拟机可用的最大处理器数，不小于一个
        int threadCount = Runtime.getRuntime().availableProcessors();
        tasks = new DispatcherTask[threadCount];

        for (int i = 0; i < threadCount; i++) {
            DispatcherTask task = new DispatcherTask(requestQueue);
            task.start();
            // 方便stop方法做清理
            tasks[i] = task;
        }
    }

    public void stop(){
        if (tasks.length > 0){
            for (DispatcherTask task:tasks) {
                // 如果任务没有中断
                if (!task.isInterrupted()){
                    task.interrupt();
                }
            }
        }
    }

}
