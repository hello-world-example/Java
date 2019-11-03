package xyz.kail.demo.java.se.temp;

import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.slf4j.impl.SimpleLogger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

//        https://github.com/apache/httpcomponents-client/commit/70489c4bb03491b6ea0bec60904fc78782963a3a#diff-54e2f21af41829eebd8e809f58bd490a
public class HttpClientMain {

    static {
        // slf4j-simple , 日志级别设置为 Debug
        System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
    }


    public static void main(String[] args) throws IOException {

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(500)
                .setSocketTimeout(2_000)
                .setCookieSpec(CookieSpecs.STANDARD)
                .build();

        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie clientCookie = new BasicClientCookie("asd", "asd");
        clientCookie.setDomain(".baidu.cn");
        clientCookie.setPath("/");
         clientCookie.setAttribute(ClientCookie.DOMAIN_ATTR, "true");
        cookieStore.addCookie(clientCookie);

        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build()) {
            HttpGet request = new HttpGet("http://www.baidu.cn");

            request.setConfig(requestConfig);
//
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            }
        }
    }

}
