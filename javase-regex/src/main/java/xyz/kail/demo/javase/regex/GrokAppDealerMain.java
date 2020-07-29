package xyz.kail.demo.javase.regex;

import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import xyz.kail.demo.javase.common.PropertiesUtil;
import xyz.kail.demo.javase.crypto.DesUtil;

import java.io.FileReader;
import java.util.Map;

/**
 * @author kail
 */
public class GrokAppDealerMain {

    static final String key = PropertiesUtil.getStringDefaultBlank("des.key");

    static Grok fullMessageGrok() {
        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerDefaultPatterns();
        grokCompiler.registerPatternFromClasspath("/patterns/java");
        grokCompiler.register("ResinThread", "resin-port-%{INT:port}-%{INT:threadId}");
        grokCompiler.register("DealerAccess", "%{DATA} Ttp ：%{DATA:tp}，info : %{GREEDYDATA:info}");

        return grokCompiler.compile("%{TIME} \\[INFO \\] \\[%{ResinThread}\\] \\[%{JAVACLASS}:%{NUMBER:line}\\]%{DealerAccess}");
    }

    public static void main(String[] args) throws Exception {

        Grok fullMessageGrok = fullMessageGrok();

        String csvFile = "/Users/kail/Downloads/graylog-search-result-absolute-2020-07-28T23_10_00.000Z-2020-07-28T23_11_00.000Z.csv";

        CSVFormat csvFormat = CSVFormat.EXCEL.withTrim()
                .withSystemRecordSeparator().
                        withFirstRecordAsHeader();

        try (FileReader fileReader = new FileReader(csvFile)) {
            try (CSVParser parser = new CSVParser(fileReader, csvFormat)) {
                for (CSVRecord csvRecord : parser) {
                    String fullMessage = csvRecord.get("full_message");
                    // System.out.println(fullMessage);

                    Match gm = fullMessageGrok.match(fullMessage);
                    final Map<String, Object> capture = gm.capture();

                    // System.out.println(capture.get("info"));
                    String tpJson = DesUtil.decryptDesWithBase64(capture.get("tp").toString(), key);
                    System.out.println(tpJson);
                }
            }
        }


    }


}
