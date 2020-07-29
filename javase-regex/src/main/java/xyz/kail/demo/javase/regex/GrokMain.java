package xyz.kail.demo.javase.regex;

import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;

import java.util.Map;

/**
 * @author kail
 */
public class GrokMain {

    public static void main(String[] args) {
        /* Create a new grokCompiler instance */
        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        // jar 包内置的: /patterns/patterns
        grokCompiler.registerDefaultPatterns();

        Map<String, String> defaultPatternDefinitions = grokCompiler.getPatternDefinitions();
        printMap("defaultPatternDefinitions", defaultPatternDefinitions);


        /* Grok pattern to compile, here httpd logs */
        final Grok grok = grokCompiler.compile("%{COMBINEDAPACHELOG}");

        /* Line of log to match */
        String log = "112.169.19.192 - - [06/Mar/2013:01:36:30 +0900] \"GET / HTTP/1.1\" 200 44346 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.152 Safari/537.22\"";

        Match gm = grok.match(log);

        /* Get the map with matches */
        final Map<String, Object> capture = gm.capture();
        printMap("capture", capture);
    }

    public static void printMap(String name, Map<String, ?> map) {
        System.out.println(name + ": ");
        map.forEach((key, value) -> System.out.println("  " + key + "  " + value));
        System.out.println();
    }

}
