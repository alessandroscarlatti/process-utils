package com.scarlatti

import com.scarlatti.process.ProcessUtil
import com.scarlatti.taskLauncher.GuiTaskLauncher
import org.junit.Test
import spock.lang.Specification

import javax.swing.JOptionPane
import java.awt.Color
import java.nio.file.Paths

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Wednesday, 3/21/2018
 */
class StoppableProcessTest extends Specification {

    Process process

    @Test
    "can start process then stop it"() {
        expect:
        startProcess()
    }

    @Test
    "start and stop"() {
        expect:
        GuiTaskLauncher.gui()
                .withTask("Start", {startProcess()})
                .withTask("Stop", {stopProcessAutomatically()})
                .withGradient(Color.decode("#e3e3e3"), Color.decode("#343434"))
                .build().show()
    }

    private void startProcess() {
        ProcessBuilder processBuilder = new ProcessBuilder(Paths.get(System.getProperty("com.scarlatti.projectDir"), "gradlew.bat").toString(), "devServer")
        processBuilder.redirectError()
        process = processBuilder.start()

        println "started process ${ProcessUtil.getProcessID(process)}"

        while (process.isAlive()) {
            process.inputStream.eachLine { line ->
                println line
            }
        }

        println "process finished!"
    }

    @Test
    "stop process"() {
        expect:
            ProcessUtil.stopProcess(3348)
    }

    void stopProcessInteractively() {
        int pid = new Integer(JOptionPane.showInputDialog("PID:"));
        ProcessUtil.stopProcess(pid);
    }

    void stopProcessAutomatically() {
        ProcessUtil.stopProcess(process)
    }


}
