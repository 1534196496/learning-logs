+ SpringApplication.run
   + 创建一个计时器，用于记录启动时间 
   + 计时开始  
   + 设置该应用程序,即使没有检测到显示器,也允许其启动.
   + 创建监听器(META-INF/spring.factories 文件中)，用于发布启动过程中的事件
      + 先加载 META-INF/spring.factories 的所有配置，并缓存
      + 再实例化加载配置中的SpringApplicationRunListener
   + 启动监听器
   + 准备环境
   + 准备context
   + 刷新context
   + afterRefresh （空方法，拓展点，上下文刷新后）
   + 计时结束
   + 发布context启动好事件
   + 依次调用 ApplicationRunner 、CommandLineRunner 
    
    
~~~ java


public ConfigurableApplicationContext run(String... args) {
        // 创建一个计时器，计算启动时间
		StopWatch stopWatch = new StopWatch();
        // 计时开始
		stopWatch.start();
		ConfigurableApplicationContext context = null;
		Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
		configureHeadlessProperty();
		SpringApplicationRunListeners listeners = getRunListeners(args);
		listeners.starting();
		try {
			ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
			ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);
			configureIgnoreBeanInfo(environment);
			Banner printedBanner = printBanner(environment);
			context = createApplicationContext();
			exceptionReporters = getSpringFactoriesInstances(SpringBootExceptionReporter.class,
					new Class[] { ConfigurableApplicationContext.class }, context);
			prepareContext(context, environment, listeners, applicationArguments, printedBanner);
			refreshContext(context);
			afterRefresh(context, applicationArguments);
			stopWatch.stop();
			if (this.logStartupInfo) {
				new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), stopWatch);
			}
			listeners.started(context);
			callRunners(context, applicationArguments);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, exceptionReporters, listeners);
			throw new IllegalStateException(ex);
		}

		try {
			listeners.running(context);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, exceptionReporters, null);
			throw new IllegalStateException(ex);
		}
		return context;
	}
~~~    