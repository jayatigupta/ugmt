package com.ugmt.client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

/**
 * 
 * @author Puneet
 * 
 */
public abstract class AbstractServiceClient {

    private static final int BUFFER_SIZE = 1028;
    private static final String CONTENT_TYPE = "content-type";
    private static final String APPLICATION_JSON_UTF = "application/json;charset=UTF-8";
    private static final String APPLICATION_JSON = "application/json";

    private static HttpClient httpClient = getThreadSafeClient();;
    private static Gson gson = new Gson();
    
    public static DefaultHttpClient getThreadSafeClient() {
        DefaultHttpClient client = new DefaultHttpClient();
        ClientConnectionManager mgr = client.getConnectionManager();
        HttpParams params = client.getParams();
        client = new DefaultHttpClient(new ThreadSafeClientConnManager(mgr.getSchemeRegistry()), params);
        return client;
    }

    /**
     * Does HTTP GET invocation and return response of type <T>
     * 
     * @param endPointUrl
     *            the http/https url
     * @param request
     *            the request body
     * @param <R>
     *            the expected payload type
     * @param <T>
     *            the response type
     * @param customHeaders
     *            custom header that should be pass along with this request
     * @return
     * @throws RuntimeException
     */
    public <T> T get(String endPointUrl, Class<T> typeClass, Map<String, String> customHeaders) throws RuntimeException {
        try {
            final Map<String, String> headers = buildCustomHeaders(customHeaders);
            // LOGGER.info("executing GET {} with headers {}", endPointUrl, headers);
            HttpGet get = new HttpGet(endPointUrl);
            get.addHeader("content-type", APPLICATION_JSON_UTF);
            get.addHeader("Content-Type", APPLICATION_JSON_UTF);
            get.addHeader("Accept", APPLICATION_JSON);
            setHttpHeaders(get, headers);
            HttpResponse httpResponse = httpClient.execute(get);
            handleResponse(httpResponse);
            return gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), typeClass);
        } catch (IOException e) {
            throw new RuntimeException("Error in GET", e.getCause());
        }
    }

    /**
     * Does HTTP POST invocation and return response of type <T>
     * 
     * @param endPointUrl
     *            the http/https url
     * @param request
     *            the request body
     * @param <R>
     *            the expected payload type
     * @param <T>
     *            the response type
     * @param customHeaders
     *            custom header that should be pass along with this request
     * @return
     * @throws RuntimeException
     */
    public <T> T post(String endPointUrl, Object request, Class<T> typeClass, Map<String, String> customHeaders)
            throws RuntimeException {
        try {
            final Map<String, String> headers = buildCustomHeaders(customHeaders);
            // LOGGER.info("executing PUT at {} with headers {}", endPointUrl, headers);
            
//            String jsonValue = mapper.writeValueAsString(request);
            
            String jsonValue = gson.toJson(request);
            System.out.println(jsonValue);
            HttpEntity requestEntity = new StringEntity(jsonValue);

            HttpPost post = new HttpPost(endPointUrl);

            post.addHeader("content-type", APPLICATION_JSON_UTF);
            post.addHeader("Content-Type", APPLICATION_JSON_UTF);
            post.addHeader("Accept", APPLICATION_JSON);
            setHttpHeaders(post, headers);

            post.setEntity(requestEntity);
            HttpResponse httpResponse = httpClient.execute(post);

            handleResponse(httpResponse);
            return gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), typeClass);
        } catch (IOException e) {
            throw new RuntimeException("Error in POST", e);
        }
    }

    /**
     * Does HTTP PUT invocation and return response of type <T>
     * 
     * @param endPointUrl
     *            the http/https url
     * @param request
     *            the ServiceRequest of type <T>
     * @param <T>
     *            the response type
     * @param <R>
     *            the expected payload type
     * @param customHeaders
     *            custom header that should be pass along with this request
     * 
     * @return
     * 
     */
    public <T> T put(String endPointUrl, Object request, Class<T> typeClass, Map<String, String> customHeaders) {

        try {
            final Map<String, String> headers = buildCustomHeaders(customHeaders);
            // LOGGER.info("executing PUT at {} with headers {}", endPointUrl, headers);
            String jsonValue = gson.toJson(request);
            HttpEntity requestEntity = new StringEntity(jsonValue);

            HttpPut put = new HttpPut(endPointUrl);

            put.addHeader("content-type", APPLICATION_JSON_UTF);
            put.addHeader("Content-Type", APPLICATION_JSON_UTF);
            put.addHeader("Accept", APPLICATION_JSON);
            setHttpHeaders(put, headers);

            put.setEntity(requestEntity);
            HttpResponse httpResponse = httpClient.execute(put);
            handleResponse(httpResponse);
            return gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), typeClass);
        } catch (Exception e) {
            throw new RuntimeException("Error in PUT", e.getCause());
        }
    }

    /**
     * Does HTTP DELETE invocation
     * 
     * @param endPointUrl
     *            the url resource to be delete
     * @param request
     *            the request body
     * @param <T>
     *            the response type
     * @param <R>
     *            the expected payload type
     * 
     * @param customHeaders
     *            custom header that should be pass along with this request
     */
    public <T> T delete(String endPointUrl, Class<T> typeClass, Map<String, String> customHeaders) {
        try {
            final Map<String, String> headers = buildCustomHeaders(customHeaders);
            // LOGGER.info("executing DELETE {} with headers {}", endPointUrl, headers);
            HttpDelete delete = new HttpDelete(endPointUrl);

            delete.addHeader(CONTENT_TYPE, APPLICATION_JSON_UTF);
            delete.addHeader("Content-Type", APPLICATION_JSON_UTF);
            delete.addHeader("Accept", APPLICATION_JSON);
            setHttpHeaders(delete, headers);

            HttpResponse httpResponse = httpClient.execute(delete);
            handleResponse(httpResponse);
            return gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), typeClass);
        } catch (Exception e) {
            throw new RuntimeException("Error in delete", e.getCause());
        }
    }

    protected Map<String, String> buildCustomHeaders(Map<String, String> customHeaders) {
        Map<String, String> headers = new HashMap<String, String>();
        if (customHeaders != null) {
            headers.putAll(customHeaders);
        }
        return headers;
    }

    private void setHttpHeaders(HttpRequestBase method, Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            method.addHeader(key, value);
        }
    }

    private void handleResponse(HttpResponse httpResponse) throws RuntimeException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            // LOGGER.error("Request failed: " + httpResponse.getStatusLine());
        } else {
            return;
        }
        // Get hold of the response entity
        HttpEntity entity = httpResponse.getEntity();

        StringBuffer sb = new StringBuffer();

        // If the response does not enclose an entity, there is no need
        // to bother about connection release
        byte[] buffer = new byte[BUFFER_SIZE];
        if (entity != null) {
            try {
                InputStream inputStream = entity.getContent();
                int bytesRead = 0;
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                while ((bytesRead = bis.read(buffer)) != -1) {
                    String chunk = new String(buffer, 0, bytesRead);
                    sb.append(chunk);
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        throw new RuntimeException("Error in response : " + sb.toString());
    }
}
