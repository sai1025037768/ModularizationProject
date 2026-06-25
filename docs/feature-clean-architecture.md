# Feature Clean Architecture

本文档记录 feature 内部的基础分层约定。当前 `feature-accommodation`、`feature-transportation` 和 `feature-attraction` 已按此方式迁移，后续其它业务模块可以按同样方式推进。

## 推荐包结构

```text
feature-xxx
  presentation/
    Activity、Fragment、页面导航协作、UI 状态组装

  domain/
    model/
    repository/
    usecase/

  data/
    remote/
    local/
    mapper/
    repository/

  contract/
    feature-api 与 domain 的契约适配 mapper

  di/
    手写装配或 DI 入口

  router/arouter/
    ARouter 注解入口、IProvider 适配、框架注册胶水
```

## 依赖方向

```text
presentation -> domain
presentation -> feature-*-api
presentation -> core-navigation/core-ui/core-model

data -> domain
data -> core-network/core-storage

contract -> domain + feature-*-api

di -> domain + data

provider -> domain usecase + contract mapper

router/arouter -> presentation/root provider + feature-*-api

domain -> pure Java contracts only
```

## 关键约定

- `domain` 不依赖 Android、ARouter、Retrofit、RxJava、`core-network`。
- `data` 实现 `domain.repository`，负责网络、本地存储、DTO 和 mapper。
- `contract` 负责把内部 domain model 映射为 `feature-*-api` 对外 Data，避免 data 层反向知道 API 契约。
- `presentation` 只处理页面渲染、用户事件和页面级导航协作。
- `feature-*-api` 仍然只放对外契约，例如 `Routes`、路由参数 key、`Provider`、对外 Data。
- 路由参数 key 由被跳转的业务 API 持有，例如 `AccommodationRoutes.MainActivityParams.CITY_ID`，调用方和接收方都引用同一个常量。
- `ProviderImpl` 这类跨模块入口应该尽量薄，只做契约转换和 usecase 调用。
- `router/arouter` 是 ARouter 注解隔离层，`@Route` 和 `IProvider` 不应该散落到业务页面或业务 Provider 实现中。
- `checkModuleBoundaries` 会自动校验已经采用 `domain/data/presentation/di/router` 包结构的 feature 分层边界，并禁止路由参数 key 以内联字符串形式散落在调用点。
