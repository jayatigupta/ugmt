package com.ugmt.client.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.ugmt.client.AbstractServiceClient;
import com.ugmt.common.dto.AuthnContext;
import com.ugmt.common.dto.AuthnResponse;

public class AuthnServiceClient extends AbstractServiceClient {
	private static String endPointUrl = "http://localhost:8080/ugmt-core/rest/authn/";

	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		AuthnServiceClient client = new AuthnServiceClient();
		AuthnContext authnContext = new AuthnContext();
		authnContext.setLoginUid("punit.kj@gmail.com");
		authnContext.setPassword("password");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("appsecret", "apps3cr3t");
		AuthnResponse authnResponse = client.post(endPointUrl, authnContext, AuthnResponse.class, headers);
		System.out.println(authnResponse.getStatus());
		System.out.println(authnResponse.getMsg());
	}
}
