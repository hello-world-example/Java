package xyz.kail.demo.javase.lang.management.tool;

import java.lang.management.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author kail
 */
public final class JmxMetricsUtil {

    /**
     * 单位转换
     */
    public static final class Unit {

        private Unit() {
        }

        public static final double KB = 1024D;
        public static final double MB = 1024D * KB;
        public static final double GB = 1024D * MB;

        public static double kb(double b) {
            return b / KB;
        }

        public static double mb(double b) {
            return b / MB;
        }

        public static double gb(double b) {
            return b / GB;
        }
    }

    /**
     * 类加载信息
     */
    public static final class ClassLoading {

        private ClassLoading() {
        }

        private static final ClassLoadingMXBean LOADING_MX_BEAN = ManagementFactory.getClassLoadingMXBean();

        /**
         * 已加载类总数
         */
        public static long getTotalLoadedClassCount() {
            return LOADING_MX_BEAN.getTotalLoadedClassCount();
        }


        /**
         * 已加载当前类
         */
        public static int getLoadedClassCount() {
            return LOADING_MX_BEAN.getLoadedClassCount();
        }


        /**
         * 已卸载类总数
         */
        public static long getUnloadedClassCount() {
            return LOADING_MX_BEAN.getUnloadedClassCount();
        }

    }

    /**
     * 编译信息
     */
    public static final class Compilation {

        private Compilation() {
        }


        private static final CompilationMXBean COMPILATION_MX_BEAN = ManagementFactory.getCompilationMXBean();

        /**
         * JIT编译器名称
         */
        public static String getName() {
            return COMPILATION_MX_BEAN.getName();
        }

        /**
         * 判断jvm是否支持编译时间的监控
         */
        public static boolean isCompilationTimeMonitoringSupported() {
            return COMPILATION_MX_BEAN.isCompilationTimeMonitoringSupported();
        }

        /**
         * 总编译时间
         */
        public static long getTotalCompilationTime() {
            if (!isCompilationTimeMonitoringSupported()) {
                return -1L;
            }
            return COMPILATION_MX_BEAN.getTotalCompilationTime();
        }

    }

    /**
     * 操作系统信息
     */
    public static final class OperatingSystem {

        private OperatingSystem() {
        }

        private static final OperatingSystemMXBean SYSTEM_MX_BEAN = ManagementFactory.getOperatingSystemMXBean();

        private static final boolean IS_OPERATING_SYSTEM_IMPL;

        static {
            IS_OPERATING_SYSTEM_IMPL = "sun.management.OperatingSystemImpl".equals(SYSTEM_MX_BEAN.getClass().getName());
        }

        /**
         * 系统名称
         * <p>
         * 相当于 System.getProperty("os.name")
         */
        public static String getName() {
            return SYSTEM_MX_BEAN.getName();
        }

        /**
         * 系统版本
         * <p>
         * 相当于System.getProperty("os.version")
         */
        public static String getVersion() {
            return SYSTEM_MX_BEAN.getVersion();
        }

        /**
         * 操作系统的架构
         * <p>
         * 相当于System.getProperty("os.arch")
         */
        public static String getArch() {
            return SYSTEM_MX_BEAN.getArch();
        }

        /**
         * 可用的内核数
         * <p>
         * 相当于 Runtime.availableProcessors()
         */
        public static int getAvailableProcessors() {
            return SYSTEM_MX_BEAN.getAvailableProcessors();
        }

        /**
         * 获取系统负载平均值
         *
         * @since 1.6
         */
        public static double getSystemLoadAverage() {
            return SYSTEM_MX_BEAN.getSystemLoadAverage();
        }

        /**
         * public native long getCommittedVirtualMemorySize();
         * <p>
         * public native long getFreeSwapSpaceSize();
         * <p>
         * public native long getFreePhysicalMemorySize();
         * <p>
         * public native long getMaxFileDescriptorCount();
         * <p>
         * public native long getOpenFileDescriptorCount();
         * <p>
         * public native long getProcessCpuTime();
         * <p>
         * public native double getProcessCpuLoad();
         * <p>
         * public native double getSystemCpuLoad();
         * <p>
         * public native long getTotalPhysicalMemorySize();
         * <p>
         * public native long getTotalSwapSpaceSize();
         */
        public static class Impl {

            private Impl() {
            }

            public static boolean isOperatingSystemImpl() {
                return IS_OPERATING_SYSTEM_IMPL;
            }

            private static long getLong(String methodName) {
                try {
                    final Method method = OperatingSystem.SYSTEM_MX_BEAN.getClass().getMethod(methodName, (Class<?>[]) null);
                    method.setAccessible(true);
                    return (long) method.invoke(OperatingSystem.SYSTEM_MX_BEAN, (Object[]) null);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    // Do Noting
                }
                return -1L;
            }

            private static double getDouble(String methodName) {
                try {
                    final Method method = OperatingSystem.SYSTEM_MX_BEAN.getClass().getMethod(methodName, (Class<?>[]) null);
                    method.setAccessible(true);
                    return (double) method.invoke(OperatingSystem.SYSTEM_MX_BEAN, (Object[]) null);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    // Do Noting
                }
                return -1D;
            }

            public static long getCommittedVirtualMemorySize() {
                if (!isOperatingSystemImpl()) {
                    return -1L;
                }
                return getLong("getCommittedVirtualMemorySize");
            }

            public static long getTotalSwapSpaceSize() {
                if (!isOperatingSystemImpl()) {
                    return -1L;
                }
                return getLong("getTotalSwapSpaceSize");
            }

            public static long getFreeSwapSpaceSize() {
                if (!isOperatingSystemImpl()) {
                    return -1L;
                }
                return getLong("getFreeSwapSpaceSize");
            }

            public static long getProcessCpuTime() {
                if (!isOperatingSystemImpl()) {
                    return -1L;
                }
                return getLong("getProcessCpuTime");
            }

            public static long getFreePhysicalMemorySize() {
                if (!isOperatingSystemImpl()) {
                    return -1L;
                }
                return getLong("getFreePhysicalMemorySize");
            }

            public static long getTotalPhysicalMemorySize() {
                if (!isOperatingSystemImpl()) {
                    return -1L;
                }
                return getLong("getTotalPhysicalMemorySize");
            }

            public static long getOpenFileDescriptorCount() {
                if (!isOperatingSystemImpl()) {
                    return -1L;
                }
                return getLong("getOpenFileDescriptorCount");
            }

            public static long getMaxFileDescriptorCount() {
                if (!isOperatingSystemImpl()) {
                    return -1L;
                }
                return getLong("getMaxFileDescriptorCount");
            }

            public static double getSystemCpuLoad() {
                if (!isOperatingSystemImpl()) {
                    return -1D;
                }
                return getDouble("getSystemCpuLoad");
            }

            public static double getProcessCpuLoad() {
                if (!isOperatingSystemImpl()) {
                    return -1D;
                }
                return getDouble("getProcessCpuLoad");
            }

        }
    }

    /**
     * 运行时信息
     */
    public static final class Runtime {

        private Runtime() {
        }

        private static final RuntimeMXBean RUNTIME_MX_BEAN = ManagementFactory.getRuntimeMXBean();

        /**
         * pid@主机名 = vmId
         */
        public static String getName() {
            return RUNTIME_MX_BEAN.getName();
        }

        /**
         * 进程ID
         */
        public static String getPid() {
            return RUNTIME_MX_BEAN.getName().split("@")[0];
        }

        /**
         * 引导类路径
         */
        public static String getBootClassPath() {
            return RUNTIME_MX_BEAN.getBootClassPath();
        }

        /**
         * 库路径
         */
        public static String getLibraryPath() {
            return RUNTIME_MX_BEAN.getLibraryPath();
        }

        /**
         * 类路径
         */
        public static String getClassPath() {
            return RUNTIME_MX_BEAN.getClassPath();
        }

        /**
         * jvm规范名称
         */
        public static String getSpecName() {
            return RUNTIME_MX_BEAN.getSpecName();
        }

        /**
         * jvm规范运营商
         */
        public static String getSpecVendor() {
            return RUNTIME_MX_BEAN.getSpecVendor();
        }

        /**
         * jvm规范版本
         * <p>
         * 1.8
         */
        public static String getSpecVersion() {
            return RUNTIME_MX_BEAN.getSpecVersion();
        }

        /**
         * jvm名称
         * <p>
         * 相当于System.getProperty("java.vm.name")
         */
        public static String getVmName() {
            return RUNTIME_MX_BEAN.getVmName();
        }

        /**
         * jvm运营商
         * <p>
         * 相当于System.getProperty("java.vm.vendor")
         */
        public static String getVmVendor() {
            return RUNTIME_MX_BEAN.getVmVendor();
        }

        /**
         * jvm实现版本
         * <p>
         * 相当于System.getProperty("java.vm.version")
         * <p>
         * 25.131-b11
         */
        public static String getVmVersion() {
            return RUNTIME_MX_BEAN.getVmVersion();
        }

        public static String getManagementSpecVersion() {
            return RUNTIME_MX_BEAN.getManagementSpecVersion();
        }

        /**
         * jvm启动时间（毫秒）
         */
        public static long getStartTime() {
            return RUNTIME_MX_BEAN.getStartTime();
        }

        /**
         * jvm正常运行时间（毫秒）
         */
        public static long getUptime() {
            return RUNTIME_MX_BEAN.getUptime();
        }

        /**
         * 获取系统属性
         */
        public static Map<String, String> getSystemProperties() {
            return RUNTIME_MX_BEAN.getSystemProperties();
        }

        /**
         * JVM 启动参数
         */
        public static List<String> getInputArguments() {
            return RUNTIME_MX_BEAN.getInputArguments();
        }


    }

    /**
     * 线程
     */
    public static final class Thread {

        private Thread() {
        }

        private static final ThreadMXBean THREAD_MX_BEAN = ManagementFactory.getThreadMXBean();

        /**
         * 返回当前活动线程数，包括守护和非守护线程
         */
        public static int getThreadCount() {
            return THREAD_MX_BEAN.getThreadCount();
        }

        /**
         * 峰值活动线程数
         */
        public static int getPeakThreadCount() {
            return THREAD_MX_BEAN.getPeakThreadCount();
        }

        /**
         * JVM 启动以来创建的线程总数
         */
        public static long getTotalStartedThreadCount() {
            return THREAD_MX_BEAN.getTotalStartedThreadCount();
        }

        /**
         * 守护线程数
         */
        public static long getDaemonThreadCount() {
            return THREAD_MX_BEAN.getDaemonThreadCount();
        }

        /**
         * 获取所有的线程信息
         */
        public static Map<Long, ThreadInfo> getAllThreadInfo() {
            long[] threadIds = THREAD_MX_BEAN.getAllThreadIds();
            Map<Long, ThreadInfo> allThreadInfo = new HashMap<>(threadIds.length * 2);

            for (long threadId : threadIds) {
                ThreadInfo threadInfo = THREAD_MX_BEAN.getThreadInfo(threadId);
                allThreadInfo.put(threadId, threadInfo);
            }
            return allThreadInfo;
        }

        /**
         * 所有的死锁线程
         */
        public static Map<Long, ThreadInfo> getAllDeadlockedThreadInfo() {
            long[] threadIds = THREAD_MX_BEAN.findDeadlockedThreads();
            Map<Long, ThreadInfo> allDeadlockedThreadInfo = new HashMap<>(threadIds.length * 2);

            for (long threadId : threadIds) {
                ThreadInfo threadInfo = THREAD_MX_BEAN.getThreadInfo(threadId);
                allDeadlockedThreadInfo.put(threadId, threadInfo);
            }
            return allDeadlockedThreadInfo;
        }

    }

    /**
     * MemoryManager
     */
    public static final class Memory {

        private Memory() {
        }


        /**
         * 内存信息
         */
        private static final MemoryMXBean MEMORY_MX_BEAN = ManagementFactory.getMemoryMXBean();

        /**
         * 内存区域
         */
        private static final Map<String, MemoryPoolMXBean> POOLS = new HashMap<>();

        static {
            List<MemoryPoolMXBean> memoryPool = ManagementFactory.getMemoryPoolMXBeans();
            for (MemoryPoolMXBean pool : memoryPool) {
                POOLS.put(pool.getName(), pool);
            }
        }

        /**
         * 垃圾收集器
         */
        private static final Map<String, GarbageCollectorMXBean> COLLECTORS = new HashMap<>();

        static {
            List<GarbageCollectorMXBean> garbageCollectors = ManagementFactory.getGarbageCollectorMXBeans();
            for (GarbageCollectorMXBean garbageCollector : garbageCollectors) {
                COLLECTORS.put(garbageCollector.getName(), garbageCollector);
            }
        }

        /**
         * 内存区域
         */
        public enum PoolEnum {


            /**
             * -XX:+UseSerialGC
             */
            EDEN_SPACE("Eden Space"),
            /**
             * -XX:+UseSerialGC
             */
            SURVIVOR_SPACE("Survivor Space"),
            /**
             * -XX:+UseSerialGC
             */
            TENURED_GEN("Tenured Gen"),

            /**
             *
             */
            PS_EDEN_SPACE("PS Eden Space"),
            /**
             *
             */
            PS_SURVIVOR_SPACE("PS Survivor Space"),
            /**
             *
             */
            PS_OLD_GEN("PS Old Gen"),


            /**
             * -XX:+UseConcMarkSweepGC
             */
            PAR_EDEN_SPACE("Par Eden Space"),
            /**
             * -XX:+UseConcMarkSweepGC
             */
            PAR_SURVIVOR_SPACE("Par Survivor Space"),
            /**
             * -XX:+UseConcMarkSweepGC
             */
            CMS_OLD_GEN("CMS Old Gen"),


            /**
             * -XX:+UseG1GC
             */
            G1_EDEN_SPACE("G1 Eden Space"),
            /**
             * -XX:+UseG1GC
             */
            G1_SURVIVOR_SPACE("G1 Survivor Space"),
            /**
             * -XX:+UseG1GC
             */
            G1_OLD_GEN("G1 Old Gen"),


            /**
             *
             */
            META_SPACE("Metaspace"),
            /**
             *
             */
            CODE_CACHE("Code Cache"),
            /**
             *
             */
            COMPRESSED_CLASS_SPACE("Compressed Class Space"),
            ;

            private String poolName;

            PoolEnum(String poolName) {
                this.poolName = poolName;
            }

            public String getPoolName() {
                return poolName;
            }

            public static PoolEnum enumOf(String name) {
                PoolEnum[] values = PoolEnum.values();
                for (PoolEnum value : values) {
                    if (Objects.equals(value.poolName, name)) {
                        return value;
                    }
                }
                return null;
            }
        }

        /**
         * 垃圾收集名称
         */
        public enum CollectorEnum {

            /**
             * -XX:+UseSerialGC
             */
            COPY("Copy"),

            /**
             * -XX:+UseSerialGC
             */
            MARK_SWEEP_COMPACT("MarkSweepCompact"),

            /**
             * -XX:+UseParallelOldGC
             * -XX:+UseParallelGC
             */
            PS_SCAVENGE("PS Scavenge"),

            /**
             * -XX:+UseParallelOldGC
             * -XX:+UseParallelGC
             */
            PS_MARKSWEEP("PS MarkSweep"),

            /**
             * -XX:+UseConcMarkSweepGC
             * -XX:+UseParNewGC
             */
            PAR_NEW("ParNew"),

            /**
             * -XX:+UseConcMarkSweepGC
             * -XX:+UseParNewGC
             */
            CONCURRENT_MARK_SWEEP("ConcurrentMarkSweep"),

            /**
             * -XX:+UseG1GC
             */
            G1_YOUNG("G1 Young Generation"),

            /**
             * -XX:+UseG1GC
             */
            G1_OLD("G1 Old Generation"),
            ;

            private String collectorName;

            CollectorEnum(String collectorName) {
                this.collectorName = collectorName;
            }

            public String getCollectorName() {
                return collectorName;
            }

            public static CollectorEnum enumOf(String name) {
                CollectorEnum[] values = CollectorEnum.values();
                for (CollectorEnum value : values) {
                    if (Objects.equals(value.collectorName, name)) {
                        return value;
                    }
                }
                return null;
            }


        }


        /**
         * 堆内内存使用量
         * <pre>
         *        +----------------------------------------------+
         *        +////////////////           |                  +
         *        +////////////////           |                  +
         *        +----------------------------------------------+
         *
         *        |--------|
         *           init
         *        |---------------|
         *               used
         *        |---------------------------|
         *                  committed
         *        |----------------------------------------------|
         *                            max
         * </pre>
         */
        public static MemoryUsage getHeapMemoryUsage() {
            return MEMORY_MX_BEAN.getHeapMemoryUsage();
        }

        /**
         * 堆外内存使用量
         * <pre>
         *        +----------------------------------------------+
         *        +////////////////           |                  +
         *        +////////////////           |                  +
         *        +----------------------------------------------+
         *
         *        |--------|
         *           init
         *        |---------------|
         *               used
         *        |---------------------------|
         *                  committed
         *        |----------------------------------------------|
         *                            max
         * </pre>
         */
        public static MemoryUsage getNonHeapMemoryUsage() {
            return MEMORY_MX_BEAN.getNonHeapMemoryUsage();
        }

        /**
         * 当前 JVM 的内存区域名称
         */
        public static Set<String> getPoolNames() {
            return POOLS.keySet();
        }

        /**
         * 对应的内存区域是否存在
         */
        public static boolean existMemoryPool(PoolEnum name) {
            return POOLS.containsKey(name.getPoolName());
        }

        public static MemoryUsage getPoolUsage(PoolEnum name) {
            if (!existMemoryPool(name)) {
                return nullUsage(null);
            }
            return nullUsage(POOLS.get(name.getPoolName()).getUsage());
        }

        public static MemoryType getPoolType(PoolEnum name) {
            if (!existMemoryPool(name)) {
                return null;
            }
            return POOLS.get(name.getPoolName()).getType();
        }

        /**
         * 返回 JVM 最近在此内存池中回收未使用的对象 所花费的内存使用量
         */
        public static MemoryUsage getPoolCollectionUsage(PoolEnum name) {
            if (!existMemoryPool(name)) {
                return nullUsage(null);
            }
            return nullUsage(POOLS.get(name.getPoolName()).getCollectionUsage());
        }

        /**
         * 峰值内存使用量
         */
        public static MemoryUsage getPoolPeakUsage(PoolEnum name) {
            if (!existMemoryPool(name)) {
                return nullUsage(null);
            }
            return nullUsage(POOLS.get(name.getPoolName()).getPeakUsage());
        }


        /**
         * 以字节为单位返回此内存池的使用阈值
         */
        public static long getPoolUsageThreshold(PoolEnum name) {
            if (!existMemoryPool(name)) {
                return -1;
            }
            return POOLS.get(name.getPoolName()).getUsageThreshold();
        }

        public static long getPoolUsageThresholdCount(PoolEnum name) {
            if (!existMemoryPool(name)) {
                return -1;
            }
            return POOLS.get(name.getPoolName()).getUsageThresholdCount();
        }

        public static long getPoolCollectionUsageThreshold(PoolEnum name) {
            if (!existMemoryPool(name)) {
                return -1;
            }
            return POOLS.get(name.getPoolName()).getCollectionUsageThreshold();
        }

        public static long getPoolCollectionUsageThresholdCount(PoolEnum name) {
            if (!existMemoryPool(name)) {
                return -1;
            }
            return POOLS.get(name.getPoolName()).getCollectionUsageThresholdCount();
        }

        private static MemoryUsage nullUsage(MemoryUsage usage) {
            return null == usage ? new MemoryUsage(-1L, 0L, 0L, -1L) : usage;
        }

        /**
         * 当前 JVM 的垃圾收集器
         */
        public static Set<String> getCollectorNames() {
            return COLLECTORS.keySet();
        }

        /**
         * 是否存在对应的垃圾收集器
         */
        public static boolean existCollector(CollectorEnum collector) {
            return COLLECTORS.containsKey(collector.getCollectorName());
        }

        /**
         * 垃圾收集器次数
         */
        public static long getGarbageCollectionCount(CollectorEnum collector) {
            if (!existCollector(collector)) {
                return -1;
            }
            return COLLECTORS.get(collector.getCollectorName()).getCollectionCount();
        }

        /**
         * 垃圾回收期总耗时
         */
        public static long getGarbageCollectionTime(CollectorEnum collector) {
            if (!existCollector(collector)) {
                return -1;
            }
            return COLLECTORS.get(collector.getCollectorName()).getCollectionTime();
        }

        /**
         * 垃圾回收器 可回收的区域名称
         */
        public static List<String> getGarbageCollectionMemoryPoolNames(CollectorEnum collector) {
            if (!existCollector(collector)) {
                return new ArrayList<>();
            }
            String[] memoryPoolNames = COLLECTORS.get(collector.getCollectorName()).getMemoryPoolNames();
            return new ArrayList<>(Arrays.asList(memoryPoolNames));
        }

    }

    private JmxMetricsUtil() {
    }
}