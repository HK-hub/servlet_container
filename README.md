# 一个简单的 Servlet 容器实现
    实现了一个简单的Servlet Container，能够编写动态的Web 技术，自定义 Servlet，实现对资源的动态请求。内部将所有的对象都进行了自定义的封装实现，
    不是简单的继承我们的三剑客，而是直接完全自己封装实现了 Requset ，Response ，除此自外，我仿造 Javax.servlet 接口规范，自定义实现 Servlet ，GenericServlet， HttpServlet 接口和对象
    我们在这个基础之上，实现了自己使用 **配置文件** `web.properties` 来进行对 urlPartern , servletClass 的书写，
    又在我们能够支持配置文件的基础上，我们借助于一个 @Controller 注解的实现原理，实现了我们的 @Servlet 接口，能够支持我们书写，initParams, name , urlPaterns,startUp 参数。
    
----
    
## Request 对象自定义实现
## Response 对象自定义实现
## Servlet，GenericServlet，HttpServlet 
## Container 的核心：Catalina，Connector，Container

-----

> QQ :3161880795
> Github: HK-hub
