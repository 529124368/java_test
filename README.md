# java_test

修改注册表HKEY_CLASSES_ROOT\Applications\javaw.exe\shell\open\command，添加参数 -jar

实现双击jar包运行程序
 
 
 # xdebug install 
 - [1. xdebug2](#1-xdebug2x)
 - [2. xdebug3](#1-xdebug3x)
  
## 1. xdebug2x

```
[xdebug]
xdebug.remote_enable=1
xdebug.remote_autostart=1
xdebug.remote_host=host.docker.internal
xdebug.remote_port=9200
xdebug.remote_log=/tmp/xdebug.log
```



## 1. xdebug3x

```
[xdebug]
xdebug.mode=debug
xdebug.start_with_request=yes
xdebug.client_host=host.docker.internal
xdebug.log=/tmp/xdebug.log
xdebug.client_port=9200
```
