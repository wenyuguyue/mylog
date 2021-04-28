package com.banma.web.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.client.*;

import java.io.IOException;
import java.net.URI;

/**
 * @author lichaofu
 * @create 2021-04-27 21:09
 */
public class MyRestTemplate extends RestTemplate {
    @Override
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException {
        Assert.notNull(url, "URI is required");
        Assert.notNull(method, "HttpMethod is required");
        ClientHttpResponse response = null;

        Object var14;

        try {
            ClientHttpRequest request = this.createRequest(url, method);
            if (requestCallback != null) {
                requestCallback.doWithRequest(request);
            }
            LogBean logBean = LogBean.logBeanThreadLocal.get();
            HttpHeaders headers = request.getHeaders();
            headers.add("rid", logBean.getRid());
            headers.add("sid", logBean.getSid());
            headers.add("tid", logBean.getTid());
            headers.add("ip", logBean.getIp());
            response = request.execute();
            this.handleResponse(url, method, response);
            var14 = responseExtractor != null ? responseExtractor.extractData(response) : null;
            return (T) var14;
        } catch (IOException var12) {
            String resource = url.toString();
            String query = url.getRawQuery();
            resource = query != null ? resource.substring(0, resource.indexOf(63)) : resource;
            throw new ResourceAccessException("I/O error on" + method.name() + "request for \"" + resource + "\":" + var12.getMessage(), var12);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

}
