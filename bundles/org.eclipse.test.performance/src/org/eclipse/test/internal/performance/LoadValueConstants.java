/*******************************************************************************
 * Copyright (c) 2003, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.test.internal.performance;

/**
 * Constants that are used as environment variables and in the timer.xml file.
 * 
 * This file is replicated in a couple of places, so when you change it you need to be careful
 * to change all the copies. It is stored in:
 * 
 * <ul>
 * <li>com.ibm.etools.perfmsr.core.perf.common (in project gkcore)
 * <li>org.eclipse.perfmsr.core (in project org.eclipse.perfmsr.core)
 * </ul> 
 */
public interface LoadValueConstants {
	
//	/** # - used to start a comment. */
//	String PARM_COMMENT 	= "#";
//	
//	/** Driver= - used to specify the driver. */
//	String PARM_DRIVER 		= "Driver=";
//	
//	/** GC - used to specify a garbage collection event. */
//	String PARM_GC 			= "GC,";
//	
//	/** GCType= - tells us the style of garbage collection events. */
//	String PARM_GC_TYPE 	= "GCType=";
//	
//	/** JVM= - the contents of java.fullversion or java.runtime.version */
//	String PARM_JVM			= "JVM=";
//	
//	/** Host= - the host that the test was run on. */
//	String PARM_HOST 		= "Host=";
//	
//	/** RunTS= - used to specify the run timestamp. */
//	String PARM_RUN_TS 		= "RunTS=";
//	
//	/** SU - used to specify a plugin start up event. */
//	String PARM_START_UP 	= "SU,";
//	
//	/** Step= - used to specify an adhoc step */
//	String PARM_STEP		= "Step=";
//	
//	/** STDOUT= - specifies where the stdout is saved */
//	String PARM_STDOUT		= "STDOUT=";
//	
//	/** Test= - used to specify an adhoc test.*/
//	String PARM_TEST		= "Test=";
//	
//	/** TS - A code in the timer.del file that links a step to a time */
//	String PARM_TIMER_STEP 	= "TS,";
//	
//	/** UUID= - used to specify the UUID for th run. */
//	String PARM_UUID 		= "UUID=";
//	
//	/** Var - Variations are sticky, once set they remain set until explicitly unset. */
//	String PARM_VAR 		= "Var";
//	
//	/** Elapsed - a type of Garbage collection event. */
//	String ARG_GC_ELAPSED	= "Elapsed";
//	
//	/** Steps - a type of garbage collection event. */
//	String ARG_GC_STEPS		= "Steps";
//		
	/** 
	 * etools_perf_ctrl - Environment variable name that controls the performance instrumentation.
	 * It is a semicolon separated set of values. See the class level of
	 * documentation for TimerStep for it's valid values.
	 */
	String ENV_PERF_CTRL 	= "etools_perf_ctrl";
//	
//	String ENV_ARG_TRUE		= "true";
	
	/**
	 * Properties that may appear inside of the etools_perf_ctrl environment variable.
	 */
	interface PerfCtrl
	{
//		/** driver - Override the name of the driver. */
//		String driver		= "driver";
//		
//		/** driverdate - Date of the driver YYYYMMDDHHMM. */
//		String driverdate	= "driverdate";
//		
//		/** driverlabel - Short driver label. */
//		String driverlabel	= "driverlabel";
//		
//		/** driverstream - The driver stream, in the case of eclipse it would be 2.1.3 or 3.0.0 */
//		String driverstream = "driverstream";
//	
//		/** driverinfo - It indicates the fully qualified name of the driver info file, */
//		String driverinfo 	= "driverinfo";
//		
//		/** debug - If present we run in debug mode. The value is ignored, but by
//		 * convention true is usually used. 
//		 */
//		String debug		= "debug";
//		
//		/** host - Name of the machine that has run the test. */
//		String host			= "host";
//		
		/** log - It indicates the directory where the timer.del file is stored. */
		String log			= "log";
//		
//		/** 
//		 * shutdown - If present it means that the workbench should be shutdown after the startup code has been run.
//		 * The value is ignored, but by convention true is usually used. 
//		 */
//		String shutdown		= "shutdown";
//		
//		/** 
//		 * startup - If present it means that startup information is being requested. The value is ignored, but by
//		 * convention true is usually used. 
//		 */
//		String startup		= "startup";
//		
//		/** startuptype - It specifies the measurements that should be taken as part of the startup snapshot.
//		 * The value is an integer, and it is the same value that is used in the IPerformanceMonitor.snapshot method.
//		 * If not specified it defaults to taking a snapshot of the operating system counters. 
//		 */
//		String startuptype	= "startuptype";
//		
//		/** uploadhost - Name of the host that the results are uploaded to. */
//		String uploadhost	= "uploadhost";
//		
//		/** uploadport = Port number that the results are uploaded to. */
//		String uploadport	= "uploadport";
//		
//		/** uploaduserid - Userid that is uploading the results. This is a userid that is registered on the server. */
//		String uploaduserid	= "uploaduserid";
//		
//		/** testd - It indicates the test number that is being performed. */
//		String testd		= "testd";
//		
//		/** 
//		 * testname - It indicates the test name. If the test name already exists it is used, otherwise
//		 * a new scenario is created. This parameter can not be used with the testd parameter.
//		 */
//		String testname		= "testname";
//		
//		/** vars - A comma separated list of the variations. */
//		String vars			= "vars";
//		
//		/** 
//		 * vars+ - Argument of ENV_PERF_CTRL, a comma separated list of the variations, but unlike
//		 * var= which replaces the variations this adds to the existing (if any) variations. This is
//		 * useful for adding the cold variation, and leaving the other ones untouched.
//		 */
//		String varsAppend	= "vars+";
//	
//		/** var - This serves the same purpose as vars= this form is deprecated. */
//		String var			= "var"; 
	}
}
