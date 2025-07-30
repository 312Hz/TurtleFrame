# TurtleFrame

> 基于 spigot-api 和 AfyBroker 的 Minecraft 插件开发中间件框架

## 🌳项目结构

```bash
.
├─turtle-api		# API 接口
├─turtle-bukkit		# bukkit 插件模块
├─turtle-web		# 网页模块
├─turtle-broker		# Broker 服务插件
└─turtle-common		# 公共模块，于 API 不同，common 只用于内部模块使用，第三方开发者使用 API 无法直接接触 common 模块
```

## ⚒️技术栈

- SqlFactory -> 数据库操作工具
- SProxy -> 字节码反射代理工具