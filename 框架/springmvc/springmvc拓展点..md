+ org.springframework.web.servlet.HandlerMapping接口      处理请求的映射
  + HandlerExecutionChain getHandler(HttpServletRequest var1) throws Exception
+ org.springframework.web.servlet.HandlerInterceptor     接口拦截器
  + boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
  + void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception
  + void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception
+ HandlerAdapter  
+ HandlerMethodArgumentResolver -- 参数绑定, 类似于controller 可以直接获取 HttpServletRequest,HttpServletResponse的功能
+ Converter系列  
    + Converter  类型转换器  1对1
    + ConverterFactory  1对多
    + GenericConverter  多对多
    + ConverterRegistry  注册转换器
    + ConditionalConverter  
    + ConditionalGenericConverter

