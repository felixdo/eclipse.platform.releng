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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;

class PerformanceMonitorLinux extends PerformanceMonitor {

	private static long PAGESIZE= 4096;
	private static long JIFFIES= 10L;
	
	/**
	 * Write out operating system counters for Linux.
	 */
	protected void collectOperatingSystemCounters(Map scalars) {
		synchronized(this) {
		    /**
		     * The status values for a Linux process, that is the values that come from /proc/self/stat.
		     * The names of the variables match the man proc page.
		     */
		    StringTokenizer st= readTokens("/proc/self/stat", false);
		    if (st != null) {
				st.nextToken();		// int pid;		// Process id.
				st.nextToken();						// String comm;		// The command name.
				st.nextToken();						// String state;
				st.nextToken();		// int ppid;		// Parent process id. */
				st.nextToken();		// int pgrp;		// Process group. */    	
				st.nextToken();		// int session;
				st.nextToken();		// int ttry_nr;
				st.nextToken();		// int tpgid;
				st.nextToken();		// long flags;
				long minflt = Long.parseLong(st.nextToken());	// Minor page faults (didn't need to load a page from disk). */
				st.nextToken();		// long cminflt;	// Minor page faults for the process and it's children. */
				long majflt = Long.parseLong(st.nextToken());	// Major page faults. */
				st.nextToken();		// long	cmajflt;	// Major page faults for the process and it's children. */
				long utime = Long.parseLong(st.nextToken());	// User time in jiffies. */
				long stime = Long.parseLong(st.nextToken());	// System time in jiffies. */ 	
				st.nextToken();		// long cutime;		// User time for the process and it's children. */
				st.nextToken();		// long cstime;		// System time for the process and it's children. */
	
				addScalar(scalars, Dimensions.USER_TIME, utime*JIFFIES);			
				addScalar(scalars, Dimensions.KERNEL_TIME, stime*JIFFIES);			
				addScalar(scalars, Dimensions.CPU_TIME, (utime+stime)*JIFFIES);			
				addScalar(scalars, Dimensions.SOFT_PAGE_FAULTS, minflt);			
				addScalar(scalars, Dimensions.HARD_PAGE_FAULTS, majflt);
		    }

		    /**
		     * The status memory values values for a Linux process, that is the values that come from /proc/self/statm.
		     * The names of the variables match the man proc page.
		     */
			st= readTokens("/proc/self/statm", false);
			if (st != null) {
				st.nextToken(); 	// int size;				// Size of the process in pages
				int resident= Integer.parseInt(st.nextToken());	// Resident size in pages.
				st.nextToken(); 	// int shared;				// Shared size in pages.
				int trs= Integer.parseInt(st.nextToken()); 		// Text (code) size in pages.
				int drs= Integer.parseInt(st.nextToken()); 		// Data/Stack size in pages.
				int lrs= Integer.parseInt(st.nextToken()); 		// Library size in pages.
				// st.nextToken();		// int dt;				// Dirty pages.
	
				addScalar(scalars, Dimensions.WORKING_SET, resident*PAGESIZE);		
				addScalar(scalars, Dimensions.TRS, trs*PAGESIZE);			
				addScalar(scalars, Dimensions.DRS, drs*PAGESIZE);			
				addScalar(scalars, Dimensions.LRS, lrs*PAGESIZE);
			}
		}
	}
	
	/**
	 * Write out the global machine counters for Linux.
	 */
	protected void collectGlobalPerformanceInfo(Map scalars) {
	    /**
	     * The meminfo values for a Linux machine, that is the values that come from /proc/meminfo.
	     */
	    StringTokenizer st= readTokens("/proc/meminfo", true);
	    if (st != null) {
			st.nextToken();		// throw away label
			long total= Long.parseLong(st.nextToken());
			long used= Long.parseLong(st.nextToken());
			long free= Long.parseLong(st.nextToken());
			st.nextToken();		// long shared;
			long buffers= Long.parseLong(st.nextToken());
			long cache= Long.parseLong(st.nextToken());
	
			addScalar(scalars, Dimensions.PHYSICAL_TOTAL, total);
			addScalar(scalars, Dimensions.USED_LINUX_MEM, used);
			addScalar(scalars, Dimensions.FREE_LINUX_MEM, free);
			addScalar(scalars, Dimensions.BUFFERS_LINUX, buffers);
			addScalar(scalars, Dimensions.SYSTEM_CACHE, cache);
	    }
	}

    private StringTokenizer readTokens(String procPath, boolean skipFirst) {
        BufferedReader rdr= null;
		try {
			rdr= new BufferedReader(new FileReader(procPath));
			if (skipFirst)
			    rdr.readLine();	// throw away the heading line
			return new StringTokenizer(rdr.readLine());
		} catch (IOException e) {
		} finally {
			try {
			    if (rdr != null)
			        rdr.close();
			} catch (IOException e) {
			}
		}
		return null;
    }
}
