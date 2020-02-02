# 关键概念

> 

## 时间标准

- `GMT` (Greenwich Mean Time) : **格林威治(Greenwich)**标准时间，英国伦敦郊区的皇家 **格林威治天文台** 的标准时间，是太阳在 **格林威治上空最高点** 时的时间。因为 **公转速度的不均匀** 和 **自转速度的减慢** ，最大误差可达 16分钟，因此格林威治时间已经不再被作为标准时间使用
- **`UTC`** (Universal Time Coordinated): **协调世界时**，由原子钟报时，相对与 GMT 经度更高，可认为 **UTC = GMT+0**，只是统计方式不同
- `DST` (Daylight Saving Time): **夏令时**，又称“日光节约时制”，是一种为节约能源而人为规定地方时间的制度。各个采用夏令时的国家规则不同，大致是在天亮早的夏季人为将时间调快一小时，以充分利用光照资源，从而节约照明用电。**1986~1991年**我国曾采用了6年夏时制，因为各种原因导致了很多的混乱，因此后续取消。
- `CST` (Cxxx Standard Time): 是一个**地区时间**，不同地区的时间不一致
  - 中国标准时间：China Standard Time **UTC+8:00**
  - 美国中部时间：Central Standard Time **UTC-6:00**
  - 澳大利亚中部时间：Central Standard Time **UTC+9:30**
  - 古巴标准时间：Cuba Standard Time **UTC-4:00**

## 关键类

- `java.time` 基于 ISO_8601日历系统实现的日期时间库
  - `Year` 年
  - `YearMonth` 年月
  - `MonthDay` 日期
  - **`LocalDate` 年月日**
  - **`LocalTime` 时分秒**
  - **`LocalDateTime` 年月日 时分秒**
  - `Clock` 
  - `Duration` 时间区间
  - `Instant` 时间点
  - `ZonedDateTime` 时区
- java.time.chrono 支持对全球日历系统的扩展
- java.time.format：日期时间格式
- java.time.zone：时区信息库
- java.time.temporal：日期时间调整辅助库

## Read More

- [Java 8——日期时间工具库（java.time）](https://www.cnblogs.com/lxyit/p/9442135.html)
- Date Time [官方文档](https://docs.oracle.com/javase/tutorial/datetime/iso/index.html)