# Grok



Java Grok 基于 **正则表达式** ，用来解析 **日志、事件 等 单行文本文件** ，最终 **转换成结构化数据**，如：JSON 等

方便之处在于，可以对复杂的正则表达式进行拆分，便于维护，同时也方便取到需要的部分，避免各种嵌套 group



## Maven 依赖

``` xml
<!-- https://github.com/thekrakken/java-grok -->
<dependency>
  <groupId>io.krakens</groupId>
  <artifactId>java-grok</artifactId>
  <version>0.1.9</version>
</dependency>
```

### ~~老版本~~

``` xml
<dependency>
    <groupId>io.thekraken</groupId>
    <artifactId>grok</artifactId>
    <version>0.1.5</version>
</dependency>
```

## 示例

``` java
package xyz.kail.demo.javase.regex;

import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;

import java.util.Map;

public class GrokMain {

    public static void main(String[] args) {
        /* 创建 grokCompiler 实例 */
        GrokCompiler grokCompiler = GrokCompiler.newInstance();

        // java-grok.jar 包内置的默认模式: /patterns/patterns
        grokCompiler.registerDefaultPatterns();

        // 除此之外还有： firewalls、haproxy、java、linux-syslog、nagios、postfix、ruby
        grokCompiler.registerPatternFromClasspath("/patterns/java");
        grokCompiler.registerPatternFromClasspath("/patterns/linux-syslog");
        grokCompiler.registerPatternFromClasspath("/patterns/haproxy");

        // 或者自定义 pattern
        grokCompiler.register("Demo", "%{IP:client} %{WORD:method} %{URIPATHPARAM:request} %{NUMBER:bytes} %{NUMBER:duration}");

        // 获取注册 Pattern
        Map<String, String> defaultPatternDefinitions = grokCompiler.getPatternDefinitions();
        printMap("defaultPatternDefinitions", defaultPatternDefinitions);

        /**
         * IP (?:%{IPV6:UNWANTED}|%{IPV4:UNWANTED})
         *     IPV4 (?<![0-9])(?:(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})[.](?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})[.](?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})[.](?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2}))(?![0-9])
         *     IPV6 ((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:)))(%.+)?
         * WORD \b\w+\b
         * URIPATHPARAM %{URIPATH}(?:%{URIPARAM})?
         *     URIPATH (?:/[A-Za-z0-9$.+!*'(){},~:;=@#%_\-]*)+
         *     URIPARAM \?[A-Za-z0-9$.+!*'|(){},~@#%&/=:;_?\-\[\]]*
         * NUMBER (?:%{BASE10NUM:UNWANTED})
         *     BASE10NUM (?<![0-9.+-])(?>[+-]?(?:(?:[0-9]+(?:\.[0-9]+)?)|(?:\.[0-9]+)))
         */
        // 一个 Pattern 有 多个 子 Pattern 组合而成
        final Grok grok = grokCompiler.compile("%{Demo}");

        /* 一条日志 */
        String log = "55.3.244.1 GET /index.html 15824 0.043";
        System.out.println(log);

        Match gm = grok.match(log);

        /* 匹配结果是一个 Map，Key 为 名称或别名 %{名称:别名} */
        final Map<String, Object> capture = gm.capture();
        printMap("capture", capture);
    }

    public static void printMap(String name, Map<String, ?> map) {
        System.out.println(name + ": ");
        map.forEach((key, value) -> System.out.println("  " + key + "  " + value));
        System.out.println();
    }
}
```

### 结果

```bash
capture: 
  duration  0.043
  request  /index.html
  Demo  55.3.244.1 GET /index.html 15824 0.043
  method  GET
  bytes  15824
  URIPATH  /index.html
  client  55.3.244.1
  URIPARAM  null
```

## 格式

- `name1 regex` 名称和正则表达式中间空格分割
- `name2 %{name1}` name2 引用 name1，
- `name3 %{name1:别名}` name3 引用 name1 同时给 name1 起别名



## Read More

- Github 仓库： [thekrakken / java-grok](https://github.com/thekrakken/java-grok)
- **Graylog** » Extractors »  [Using Grok patterns to extract data](https://docs.graylog.org/en/3.3/pages/extractors.html#using-grok-patterns-to-extract-data)
- **Logstash** Reference » Filter plugins » [Grok filter plugin](https://www.elastic.co/guide/en/logstash/current/plugins-filters-grok.html)
- Grok Debugger
  - **http://grokdebug.fennbk.com/**
  - http://grokdebug.herokuapp.com/
- [Grok 内置 Pattern 解释](https://www.cnblogs.com/zhangan/p/11395056.html)

