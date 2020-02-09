# 常见操作

## Date <-Long-> Instant

```java
Instant now = Instant.now();
// Date.from 1.8 新增的方法
Date date = Date.from(now);
// date.toInstant() 1.8 新增的方法
Instant justNow = date.toInstant();

// 时间戳 转 Instant

Instant instant = Instant.ofEpochMilli(timestamp);

// Instant 转 时间戳（ Date.from 的内部实现 ）
Date date2 = new Date(now.toEpochMilli());
// 时间戳 转 Instant（ date.toInstant 的内部实现 ）
final Instant now2 = Instant.ofEpochMilli(date.getTime());
```

## Instant <-(ZonedDateTime)->  LocalDateTime

```java
// 可理解为对 时间戳 timestamp 的封装
Instant now = Instant.now();
// 转为为各个时区的时间
ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
// 转化成本地时间
LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

// 在某个时区的本地时间转化成 Instant
Instant justNow = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
```

## String 格式化

>  [java.time.format.DateTimeFormatter](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html)

```java
// 默认的字符串格式 2020-02-09T17:21:11.621
LocalDateTime.now().toString();

// 解析
LocalDateTime dateTime1 = LocalDateTime.parse("2020-02-09T17:21:11.621");
// 可以去掉毫秒
LocalDateTime dateTime2 = LocalDateTime.parse("2020-02-09T17:21:11");
// 秒也可以去掉
LocalDateTime dateTime3 = LocalDateTime.parse("2020-02-09T17:21");
// 自定义格式 解析
LocalDateTime dateTime4 = LocalDateTime.parse(
    "2020.02.09 17.31.03", 
    DateTimeFormatter.ofPattern("yyyy.MM.dd HH.mm.ss")
);


// 2020-02-09T17:38:57.354
LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
// 2020-02-09 17:38:57
LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
```

