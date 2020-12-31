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

### 5.Dispatcher

##### 用来调节请求的个数等等

### 6.Interceptor

	##### 拦截器,将复杂的网络请求逻辑分散到每个拦截器中

- RetryAndFollowUpInterceptor 重试和失败重定向拦截器
- BridgeInterceptor 桥接和适配拦截器
- CacheInterceptor 缓存拦截器
- ConnectInterceptor 连接拦截器，建立可用连接
- CallServerInterceptor 负责将我们的请求写进网络流