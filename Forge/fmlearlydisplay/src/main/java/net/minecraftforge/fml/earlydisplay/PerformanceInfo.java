/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.minecraftforge.fml.earlydisplay;

import com.sun.management.OperatingSystemMXBean;
import net.minecraftforge.fml.loading.FMLConfig;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class PerformanceInfo {

    private final boolean showCPUUsage;
    private final OperatingSystemMXBean osBean;
    private final MemoryMXBean memoryBean;

    float memory;
    private String text;

    PerformanceInfo() {
        showCPUUsage = FMLConfig.getBoolConfigValue(FMLConfig.ConfigValue.EARLY_WINDOW_SHOW_CPU);
        osBean = showCPUUsage ? ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class) : null;
        memoryBean = ManagementFactory.getMemoryMXBean();
    }

    void update() {
        final MemoryUsage heapusage = memoryBean.getHeapMemoryUsage();
        memory = (float) heapusage.getUsed() / heapusage.getMax();

        if (!showCPUUsage) {
            text = "Heap: %d/%d MB (%.1f%%) OffHeap: %d MB".formatted(heapusage.getUsed() >> 20, heapusage.getMax() >> 20, memory * 100.0, memoryBean.getNonHeapMemoryUsage().getUsed() >> 20);
            return;
        }

        var cpuLoad = osBean.getProcessCpuLoad();
        String cpuText;
        if (cpuLoad == -1) {
            cpuText = "*CPU: %.1f%%".formatted(osBean.getCpuLoad() * 100f);
        } else {
            cpuText = "CPU: %.1f%%".formatted(cpuLoad * 100f);
        }

        text = "Heap: %d/%d MB (%.1f%%) OffHeap: %d MB  %s".formatted(heapusage.getUsed() >> 20, heapusage.getMax() >> 20, memory * 100.0, memoryBean.getNonHeapMemoryUsage().getUsed() >> 20, cpuText);
    }

    String text() {
        return text;
    }

    float memory() {
        return memory;
    }
}
