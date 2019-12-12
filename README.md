<div align="center">    
 <img src="https://img.shields.io/github/license/create1st/reactor-mdc.svg" align="left" />
 <img src="https://img.shields.io/badge/projectreactor.io-3.3.1.RELEASE-green.svg" align="left" />
 <img src="https://img.shields.io/badge/PRs-welcome-green.svg" align="left" />
</div>

# reactor-mdc
This is MDC logging library for [Project Reactor](http://projectreactor.io)

# Example logs
Sample pattern is using **ContextFlow** nad **ContextId** properties defined in logger pattern
```
 %d{HH:mm:ss.SSS} [%t] [%X{ContextFlow}] [%X{ContextId}] [%-5level] %logger{15} - %msg%n
```
Sample log without **SubscriberContext** set
```java
flux.subscribe();
```
```
21:28:34.399 [main] [] [] [DEBUG] c.c.r.l.m.Demo - 0
21:28:34.399 [main] [] [] [DEBUG] c.c.r.l.m.Demo - 1
21:28:34.399 [main] [] [] [DEBUG] c.c.r.l.m.Demo - 2
21:28:34.399 [main] [] [] [DEBUG] c.c.r.l.m.Demo - 3
21:28:34.399 [main] [] [] [DEBUG] c.c.r.l.m.Demo - 4
21:28:34.399 [main] [] [] [DEBUG] c.c.r.l.m.Demo - 5
21:28:34.399 [main] [] [] [DEBUG] c.c.r.l.m.Demo - 6
21:28:34.399 [main] [] [] [DEBUG] c.c.r.l.m.Demo - 7
21:28:34.399 [main] [] [] [DEBUG] c.c.r.l.m.Demo - 8
21:28:34.400 [main] [] [] [DEBUG] c.c.r.l.m.Demo - 9
21:28:34.400 [main] [] [] [INFO ] c.c.r.l.m.Demo - [1, 3, 5, 7, 9]
21:28:34.400 [main] [] [] [INFO ] c.c.r.l.m.Demo - [0, 2, 4, 6, 8]
```
Sample log with **SubscriberContext** set to **ContextFlow** only
```java
flux.subscriberContext(MdcContext.of(Flow.Numbers))
    .subscribe();
```
```
21:28:34.295 [main] [] [] [DEBUG] r.u.Loggers$LoggerFactory - Using Slf4j logging framework
21:28:34.337 [main] [Numbers] [] [DEBUG] c.c.r.l.m.Demo - 0
21:28:34.385 [main] [Numbers] [] [DEBUG] c.c.r.l.m.Demo - 1
21:28:34.385 [main] [Numbers] [] [DEBUG] c.c.r.l.m.Demo - 2
21:28:34.385 [main] [Numbers] [] [DEBUG] c.c.r.l.m.Demo - 3
21:28:34.385 [main] [Numbers] [] [DEBUG] c.c.r.l.m.Demo - 4
21:28:34.386 [main] [Numbers] [] [DEBUG] c.c.r.l.m.Demo - 5
21:28:34.386 [main] [Numbers] [] [DEBUG] c.c.r.l.m.Demo - 6
21:28:34.386 [main] [Numbers] [] [DEBUG] c.c.r.l.m.Demo - 7
21:28:34.386 [main] [Numbers] [] [DEBUG] c.c.r.l.m.Demo - 8
21:28:34.386 [main] [Numbers] [] [DEBUG] c.c.r.l.m.Demo - 9
21:28:34.387 [main] [Numbers] [] [INFO ] c.c.r.l.m.Demo - [1, 3, 5, 7, 9]
21:28:34.388 [main] [Numbers] [] [INFO ] c.c.r.l.m.Demo - [0, 2, 4, 6, 8]
```
Sample log with **SubscriberContext** set to **ContextFlow** and **ContextId**
```java
flux.subscriberContext(MdcContext.of(Flow.Numbers, 1, "TEST"))
    .subscribe();
```
```
21:28:34.402 [main] [Numbers] [1,TEST] [DEBUG] c.c.r.l.m.Demo - 0
21:28:34.402 [main] [Numbers] [1,TEST] [DEBUG] c.c.r.l.m.Demo - 1
21:28:34.403 [main] [Numbers] [1,TEST] [DEBUG] c.c.r.l.m.Demo - 2
21:28:34.403 [main] [Numbers] [1,TEST] [DEBUG] c.c.r.l.m.Demo - 3
21:28:34.403 [main] [Numbers] [1,TEST] [DEBUG] c.c.r.l.m.Demo - 4
21:28:34.403 [main] [Numbers] [1,TEST] [DEBUG] c.c.r.l.m.Demo - 5
21:28:34.403 [main] [Numbers] [1,TEST] [DEBUG] c.c.r.l.m.Demo - 6
21:28:34.403 [main] [Numbers] [1,TEST] [DEBUG] c.c.r.l.m.Demo - 7
21:28:34.403 [main] [Numbers] [1,TEST] [DEBUG] c.c.r.l.m.Demo - 8
21:28:34.404 [main] [Numbers] [1,TEST] [DEBUG] c.c.r.l.m.Demo - 9
21:28:34.404 [main] [Numbers] [1,TEST] [INFO ] c.c.r.l.m.Demo - [1, 3, 5, 7, 9]
21:28:34.404 [main] [Numbers] [1,TEST] [INFO ] c.c.r.l.m.Demo - [0, 2, 4, 6, 8]
```
