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
