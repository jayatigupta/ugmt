package com.ugmt.core.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public final class PropertyLoader {

	public static Properties props;
	static {
		try {
			props = PropertiesLoaderUtils.loadProperties(new ClassPathResource(
					"app.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
