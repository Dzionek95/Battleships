package com.java_academy.logic.tools;

import org.testng.annotations.Test;

import java.util.Locale;

import static org.testng.Assert.assertEquals;



public class I18NTest {

	@Test
	public void testInternationalization() {
        assertEquals("Hello World!", I18NResolver.getMsgByKey("hello.world"));
	}
	
	@Test
	public void testInternationalization1() {
		String language = new String("pl");
	    String country = new String("PL");
        
        Locale defaultLocale = new Locale(language, country);
        I18NResolver.updateLocale(defaultLocale);

        assertEquals("Witaj \u015Bwiecie!", I18NResolver.getMsgByKey("hello.world"));
	}

}
