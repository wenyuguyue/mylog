package com.banma.mylog.web;

import com.banma.web.utils.LogBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.*;

import java.io.IOException;
import java.net.URI;


/**
 * Hello world!
 */
@SpringBootApplication
@RefreshScope
@RestController
@EnableDiscoveryClient
@ComponentScan({"com.banma.web.utils","com.banma.mylog"})
public class App {
    private Logger logger = LoggerFactory.getLogger("kafka");

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @RequestMapping("/test")
    public Object test() {
        LogBean logBean = new LogBean("rid", "sid", "tid", "nigix", "i an nigix", "ip", "url");
        logger.info(logBean.toString());
        return logBean;
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate() {
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
        };
    }

    ;
}
