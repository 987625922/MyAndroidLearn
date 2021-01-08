## okhttp

```java
String url = "http://wwww.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get() //默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(TAG + "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(TAG + "onFailure: "+ response.body().string());
            }
        });
```



### 1.OkHttpClient 解析

#####			OkHttpClient 主要用 Builder来赋值 OkHttpClient 类，整个初始化之后 OkHttpClient 的变量不可变

### 2.Request 解析

##### 跟Request 一样通过建造者模式创建赋值

```
Request request = new Request.Builder()
                .url(url)
                .get() //默认就是GET请求，可以不写
                .build();
```

##### 一个很经典的建造者模式赋值
### 3.Response 解析
##### Response类里面主要定义了服务器返回的相关信息，里面都是网络请求回来的信息的封装

### 4.ReallCall

##### ReallCall 是进行网络请求的类，里面有2个重要的方法 一个是同步请求execute()，一个是异步请求enqueue()，还有最后也是最重要的一个方法getResponseWithInterceptorChain()，通过这个方法来执行网络请求

### 5.Dispatcher(调度管理RealCall)

```
# Dispatcher
默认的最大并发请求量
private int maxRequests = 64;
单个host支持的最大并发量
private int maxRequestsPerHost = 5;
//异步任务等待队列
private val readyAsyncCalls = ArrayDeque<AsyncCall>()
//异步任务队列
private val runningAsyncCalls = ArrayDeque<AsyncCall>()
//同步任务队列
private val runningSyncCalls = ArrayDeque<RealCall>()
```



- ##### 	okhttp的线程池

  ```
   executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
            new SynchronousQueue<>(), Util.threadFactory("OkHttp Dispatcher", false));
  ```

  - 0: 核心线程数。即使空闲也会被保留的线程数。这里设置为0，说明没有被保留的线程，所有空闲的线程都会被终止。

  - Integer.MAX_VALUE：线程池可以容纳最大线程数量。Integer.MAX_VALUE是非常大的一个数，可以理解为OkHttp随时可以创建新的线程来满足需要。可以保证网络的I/O任务有线程来处理，不被阻塞。

  - 60, TimeUnit.SECONDS：空闲线程被终止的等待时间，这里设置为60秒。

  - new SynchronousQueue<Runnable>()：阻塞队列。SynchronousQueue是这样 一种阻塞队列，其中每个 put 必须等待一个 take。特点是不存储数据，当添加一个元素时，必须等待一个消费线程取出它，否则一直阻塞，如果当前有空闲线程则直接在这个空闲线程执行，如果没有则新启动一个线程执行任务。

  - Util.threadFactory("OkHttp Dispatcher", false)：用来创建线程的线程工厂。

### 6.Interceptor

	##### 拦截器,将复杂的网络请求逻辑分散到每个拦截器中

- RetryAndFollowUpInterceptor 重试和失败重定向拦截器
- BridgeInterceptor 桥接和适配拦截器
- CacheInterceptor 缓存拦截器
- ConnectInterceptor 连接拦截器，建立可用连接
- CallServerInterceptor 负责将我们的请求写进网络流