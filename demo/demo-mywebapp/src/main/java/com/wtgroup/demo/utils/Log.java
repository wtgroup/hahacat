package com.wtgroup.demo.utils;


import java.text.SimpleDateFormat;

/**
 * 自定义日志/调试工具类
 *
 * @version 1.1
 * @author Nisus-Liu
 */
public class Log {
	
	
	// 直接调用trace(Object o)则为1, 二次调用则为2
	private static int steNum = 1;
	// 记录标记序号
	private static int serialNum = 0;
	// 全局任务描述
	// begin()方法设置, end()方法获取并置空
	private static String global_task = null;
	
	/**
	 * 跟踪某一对象在运行时的取值情况.
	 *
	 * @param o
	 */
	public static void trace(Object o) {
		
		long ctime = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String ctime_fmt = sdf.format(ctime);
		Throwable throwable = new Throwable();      // 0
		StackTraceElement ste;
		ste = throwable.getStackTrace()[steNum];
		// 每次调用应该讲steNum归为1
		steNum = 1;
		serialNum++;
		System.out.println("[MyLog " + ctime_fmt + ", " + serialNum + "] " + o + "\t\t...at\t" + ste);
		
	}
	
	/**
	 * 仅做一次简单的标记
	 */
	public static void trace() {
		steNum = 2;     // 2次调用
		trace("");
	}
	
	
	/**
	 * 标记某一任务单元开始
	 */
	public static void begin() {
		steNum = 2;
//		trace("BEGIN: ");
		
		// 获取调用我的方法名称
		String task = new Throwable().getStackTrace()[1].getMethodName();
		// 将任务描述共享给去全局, 一遍end()使用
		global_task = task;
		// 什么都不传递时, 任务描述就是所在的方法名
		trace("BEGIN: " + task + " ");
		
	}
	
	public static void begin(String task) {
		steNum = 2;
		global_task = task;
		trace("BEGIN: " + task + " ");
	}
	
	/**
	 * 标记某一任务单元结束
	 */
	public static void end() {
		steNum = 2;
		
		// 获取调用我的方法名称
		String task = global_task;
		global_task = null;     // 全局任务描述置空
		if (task == null) {
			task = new Throwable().getStackTrace()[1].getMethodName();
			
		}
		// 什么都不传递时, 任务描述就是所在的方法名
		trace("END: " + task + " ");
		
	}
	
	
	public static void end(String task) {
		steNum = 2;
		trace("END: " + task + " ");
	}
	
	
//	@Test
//	public void row2PCDEntity() {
//		Log.trace();
//		Log.trace("方法开始");
//		Log.trace(new Object());
//		Log.trace(998);
//
//		// 可在方法调用开始时标记
//		Log.begin();
//
//		// 可在方法调用结束时标记
//		Log.end();
//
//		// 测试begin()和end()任务描述是否可以全局共享
//		Log.begin("任务开始");
//		Log.end();
//
//	}
	
}
