package com.ugmt.core.endpoint;

import java.util.List;
import javax.ws.rs.core.HttpHeaders;

import com.ugmt.common.exception.InvalidDataException;
import com.ugmt.core.util.PropertyLoader;

public abstract class AbstractService {

	protected static final String APP_SECRET = "appsecret";

	protected void validateHeader(HttpHeaders httpHeaders)
			throws InvalidDataException {
		List<String> appSecretHeaders = httpHeaders
				.getRequestHeader(APP_SECRET);
		if (appSecretHeaders == null || appSecretHeaders.size() == 0) {
			throw new InvalidDataException("app secret missing");
		}
		String appSecretHeader = httpHeaders.getRequestHeader(APP_SECRET)
				.get(0);
		if (appSecretHeader == null
				|| !appSecretHeader.equals(PropertyLoader.props
						.getProperty(APP_SECRET))) {
			throw new InvalidDataException("app secret incorrect");
		}
	}
}
