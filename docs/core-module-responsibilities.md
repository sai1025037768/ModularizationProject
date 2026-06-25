# Core Module Responsibilities

本文档用于约定当前项目中 `core-*` 模块的职责边界。规则的目标是让基础能力稳定、可复用，并避免基础层反向感知业务层。

## 总体原则

`core` 模块只提供跨业务复用的基础能力，不承载具体业务流程、业务页面和业务数据编排。

依赖方向：

```text
app
  -> feature-*-api
  -> feature-*
  -> core-*

feature-*
  -> feature-*-api
  -> core-navigation
  -> core-ui
  -> core-model
  -> core-device
  -> core-common
  -> core-network

feature-*-api
  -> biz-service-base

platform-*
  -> core-*
  -> third-party adapters

core-*
  -> third-party libraries only

biz-service-base
  -> no project modules
```

核心规则：

- `core-*` 不依赖 `app`、`feature-*`、`feature-*-api`、`biz-*`。
- `core-*` 之间当前保持独立，不互相依赖。
- `platform-*` 只做三方框架适配，可以依赖 `core-*` 抽象，但不依赖 `app`、`feature-*`、`biz-*`。
- `feature-*` 只依赖 `core-navigation` 抽象，不直接依赖 `platform-*` 适配实现。
- `biz-service-base` 只放业务服务通信的基础契约，不依赖 `core-*` 或 `feature-*`。
- 业务路由 path 和参数 key 属于业务对外契约，放到对应 `feature-*-api`。
- 业务数据模型优先放在对应 `feature-*-api`。只有多个业务都需要、且不属于某个业务线的模型，才考虑进入 core。

## :core-ui

当前职责：Android UI 基座和通用展示层工具。

可以放：

- `BaseActivity`、`BaseFragment`、基础 Adapter。
- 与 UI 组装相关的小工具，例如 `ActivityUtils`。
- 多业务复用的通用 UI 资源，例如 `layout_app_bar`。

不应该放：

- 网络请求封装、Retrofit Service、接口地址。
- 数据库存储、SharedPreferences、账号持久化。
- 住宿、出行、景点等具体业务逻辑。
- App 启动、全局装配、业务路由编排。

## :core-navigation

当前职责：框架无关的通用导航抽象。

可以放：

- `Navigation`、`Navigator`、`RouteRequest` 这类不依赖 Android/ARouter 的导航基础契约。
- 通用导航结果、导航拦截、导航上下文等业务无关抽象。

不应该放：

- 住宿、出行、景点、首页等具体业务 route path。
- 具体业务的路由参数 key。
- Activity、Fragment、Provider 实现。
- 业务数据获取逻辑。
- ARouter、Android Framework 等具体导航框架依赖。
- 网络请求、数据库和本地存储实现。
- 需要依赖具体 feature-api 的服务契约。

## :platform-navigation-arouter

当前职责：把 `core-navigation` 抽象适配到 ARouter。

可以放：

- `ArouterNavigator`。
- `ARouter.init`、`ARouter.getInstance().build(...)` 等 ARouter launcher 调用。
- `RouteRequest` 参数到 ARouter `Postcard` 参数的转换。

不应该放：

- 业务 route path 和参数 key。
- Activity、Fragment、Provider 实现。
- 住宿、出行、景点等具体业务逻辑。
- 网络、存储、UI 基座能力。

当前约定：

- 只有 app 装配 `platform-navigation-arouter`。
- feature 模块不依赖 `platform-navigation-arouter`，也不直接 import `com.alibaba.android.arouter.launcher.ARouter`。
- feature 模块只允许在 `router/arouter` 适配层使用 ARouter 的 `@Route` 和 `IProvider`，业务 `presentation`/`ProviderImpl` 不直接 import ARouter。
- 页面参数优先通过普通 Android `Intent`/`Bundle` 读取，发起导航统一走 `core-navigation`。

## :core-common

当前职责：跨业务复用的轻量契约和非 UI 工具。

可以放：

- 通用 MVP/View/Presenter 基类。
- 通用生命周期枚举。
- 纯 Java/JVM 的业务无关工具。

不应该放：

- Activity、Fragment、Adapter、Toolbar 等 UI 基座。
- `Parcelable`/`Serializable` 等跨业务共享模型。
- `Context`、`ConnectivityManager` 等 Android Framework 工具。
- 网络请求封装、Retrofit Service、接口地址。
- 数据库存储、SharedPreferences、账号持久化。
- 住宿、出行、景点等具体业务逻辑。

当前备注：

- 这个模块现在是纯 Java Library。需要 Android Framework 的公共工具不要放回这里。

## :core-model

当前职责：跨业务复用的轻量共享模型。

可以放：

- 多个 feature 都需要的行程、城市、位置等共享模型。
- Android 路由传参需要的 `Parcelable` 模型，例如当前示例里的 `TravelDetail`。

不应该放：

- Activity、Fragment、Adapter、Toolbar 等 UI 基座。
- 网络请求封装、数据库访问和 SharedPreferences。
- 具体业务页面逻辑。
- 只属于单一业务线的模型。单一业务线模型优先放到对应 `feature-*-api`。

## :core-device

当前职责：Android 设备能力和系统服务工具。

可以放：

- 网络连接状态判断，例如 `NetworkUtils`。
- 设备、系统服务、权限状态等轻量 Android Framework 工具。

不应该放：

- Retrofit/OkHttp/FastJson 等网络请求基础设施。
- Activity、Fragment、Adapter、Toolbar 等 UI 基座。
- SharedPreferences、数据库等持久化实现。
- 具体业务逻辑和业务模型。

## :core-network

当前职责：网络基础设施。

可以放：

- `ApiClient`、网络初始化入口。
- Retrofit/OkHttp/FastJson 基础封装。
- Converter、Interceptor、ServiceWrap 这类网络框架适配。
- 网络层通用配置对象。

不应该放：

- 具体业务 API 接口，例如住宿、出行、景点接口。
- 业务 DTO 到页面模型的转换。
- Activity、Fragment、ViewModel、Presenter。
- 本地存储逻辑。

当前约定：

- feature 可以依赖 `core-network` 发起自己的业务请求。
- `core-network` 不知道任何 feature，也不依赖 `core-common` 或 `core-storage`。

## :core-storage

当前职责：本地持久化基础设施。

可以放：

- SharedPreferences 封装。
- 数据库 Helper。
- 本地存储实体和基础 DAO 能力。
- App 级配置存储。

不应该放：

- 网络请求逻辑。
- 业务页面逻辑。
- 具体业务流程编排。
- feature 的 Provider 或 Router。

当前备注：

- `User` 这类实体如果未来变成真实账号业务模型，更适合迁移到账号业务模块或 `feature-account-api`。当前仍作为示例本地实体保留。

## :biz-service-base

当前职责：跨业务服务通信的最小基础契约。

可以放：

- `ResponseCallback`。
- `ErrorMessage`。
- 跨 feature-api 复用的纯 Java 契约类型。

不应该放：

- Android UI 类型。
- ARouter 使用细节。
- 网络、存储实现。
- 具体业务 Provider。

## 新增代码放置判断

遇到新代码时按下面顺序判断：

1. 是否只属于住宿、出行、景点某一块业务？放到对应 `feature-*` 或 `feature-*-api`。
2. 是否是业务间服务契约的基础类型？放到 `biz-service-base`。
3. 是否是网络基础能力？放到 `core-network`。
4. 是否是本地持久化基础能力？放到 `core-storage`。
5. 是否是业务 route path 或参数 key？放到对应 `feature-*-api`。
6. 是否是 UI 基座或展示层通用工具？放到 `core-ui`。
7. 是否是多个业务都需要的共享模型？放到 `core-model`。
8. 是否是 Android 设备能力或系统服务工具？放到 `core-device`。
9. 是否是业务无关、框架无关的导航抽象？放到 `core-navigation`。
10. 是否是导航三方框架适配？放到对应 `platform-navigation-*`。
11. 如果既不像基础能力，也不像业务契约，先不要放进 core。
