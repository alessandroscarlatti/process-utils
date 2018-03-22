package com.scarlatti.stoppableProcess;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;

import java.lang.reflect.Field;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Wednesday, 3/21/2018
 */
public class ProcessUtil {

    public static long getProcessID(Process p) {
        try {
            if (p.getClass().getName().equals("java.lang.Win32Process") ||
                p.getClass().getName().equals("java.lang.ProcessImpl")) {
                return getWindowsProcessID(p);
            } else {
                throw new IllegalStateException("Unexpected Process implementation: " + p.getClass().getName());
            }
        } catch(Exception ex) {
            throw new RuntimeException("Unable to get PID for process: " + p);
        }
    }

    public static long getWindowsProcessID(Process p) throws Exception {
            Field f = p.getClass().getDeclaredField("handle");
            f.setAccessible(true);
            long handl = f.getLong(p);
            Kernel32 kernel = Kernel32.INSTANCE;
            WinNT.HANDLE hand = new WinNT.HANDLE();
            hand.setPointer(Pointer.createConstant(handl));
            long pid = kernel.GetProcessId(hand);
            f.setAccessible(false);

            return pid;
    }

    public static void stopProcess(Process process) {
        long pid = getProcessID(process);
        stopProcess(pid);
    }

    public static void stopProcess(long pid) {
//        Kernel32 kernel = Kernel32.INSTANCE;
//        kernel.GenerateConsoleCtrlEvent(Wincon.CTRL_C_EVENT, pid);

        try {
            ProcessBuilder builder = new ProcessBuilder("taskkill", "/f", "/t", "/pid", String.valueOf(pid));
            Process process = builder.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
