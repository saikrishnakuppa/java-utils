package org.javautils;

public class StandaloneJavaAppExample {

	private final Object shutdownMonitor = new Object();
	private volatile boolean shutdown;
	
	public void start() {
		createAppThread().start();
		sleepUntilShutdown();
		exitJVM(0);
	}
	
	void exitJVM(int status) {
		System.exit(status);
	}
	
	void sleepUntilShutdown() {
		synchronized(shutdownMonitor) {
			try {
				while(!shutdown)
					shutdownMonitor.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	Thread createAppThread() {
		Thread appThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// run your application here
					System.out.println("App started successfully and online...");
				} catch(Throwable e) {
					synchronized(shutdownMonitor) {
						shutdown =  true;
						shutdownMonitor.notifyAll();
					}
				}
			}
		});
		appThread.setContextClassLoader(getClass().getClassLoader());
		appThread.setName("main-app");
		return appThread;
	}
	
	public static void main(String[] args) throws Exception {
		
		StandaloneJavaAppExample app = new StandaloneJavaAppExample();
		app.start();
	}
}
