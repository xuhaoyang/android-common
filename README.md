# Android-common
----

![android-common](https://img.shields.io/badge/android--common-0.0.2-brightgreen.svg) ![API](https://img.shields.io/badge/api-15%2B-brightgreen.svg) [![](https://img.shields.io/travis/rust-lang/rust.svg)]() [![](https://img.shields.io/badge/License-Apache%202.0-brightgreen.svg)]()
### 框架介绍

```
框架来自自己和公司项目中的经验总结，主要应用于快速开发，对Activity/Fragment提供异步处理数据功能和下拉刷新列表功能等，异步刷新利用AsyncTaskLoader进行封装使用，封装RecyclerView和SwipeRefreshLayout实现下拉刷新，整合了常用Android工具库。
```
### 目录结构
简略介绍

```
.
├── bind                          // 学习参考butterknife注解绑定功能
├── loader                        // 封装AsyncTaskLoader     
├── ui
│   ├── fragment
│   │   ├── LoaderFragment.java   // AsyncLoader异步处理
│   │   ├── RecyclerFragment.java // 整合原生下拉刷新功能
│   │   ├── ListFragment.java     // 对数据绑定进行封装
│   │   └── ...                   // 更多省略
│   ├── vh                        // ViewHolder封装和回调
│   ├── OnLoadMoreListener.java
│   ├── LoaderActivity.java
│   ├── ...
│   └── WebViewActivity.java
├── utils                         // 常用工具类（第三方+总结）
├── widget                        // 第三方或自定义View
├── Application.java
└── XHYInit.java                  // 框架初始化

```

### 如何使用
在要集成的模块 build.gradle 文件中添加依赖

```
compile 'org.blankapp:blankapp:0.0.1'
```

