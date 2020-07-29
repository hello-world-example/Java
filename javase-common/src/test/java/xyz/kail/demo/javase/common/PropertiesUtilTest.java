package xyz.kail.demo.javase.common;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PropertiesUtilTest {

    @org.junit.Test
    public void getStringDefaultBlank() {
        assertEquals("", "测试", PropertiesUtil.getStringDefaultBlank("test"));
    }

    @Test
    public void register() throws IOException {
        PropertiesUtil.register(PropertiesUtilTest.class.getResourceAsStream("/common.properties"));

        assertEquals("", "测试", PropertiesUtil.getStringDefaultBlank("test"));
        assertEquals("", "公共", PropertiesUtil.getStringDefaultBlank("common"));
    }
}