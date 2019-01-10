package com.snooker.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * @author iplus
 * @date 16/7/12
 * @e-mail iplus.wjy@gmail.com
 * @description Http工具类
 */
@Component
public class HttpUtil {
    /**
     * 从指定url获取数据
     *
     * @param url
     * @return
     */
    public String getDataFromUrl(String url) {
        String rev = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response=null;
        try {
            //添加http头信息
            response = httpclient.execute(get);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {
                rev = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
            }
        }catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            try {
                if(response!=null){
                    response.close();
                }
                //释放链接
                get.releaseConnection();
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return rev;
        }
    }

    /**
     * 从指定url通过json string参数获取数据
     *
     * @param url
     * @param jsonStr
     * @return
     */
    public String getDataFromUrlWithJSON(String url, String jsonStr) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String rev = null;
        try {
            httpPost.addHeader("charset", "UTF-8");
            HttpEntity httpEntity = null;
            try {
                httpEntity = new StringEntity(jsonStr, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            httpPost.setHeader("Content-Type", "application/json; charset=utf-8");
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            rev =  EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放链接
            httpPost.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return rev;
        }
    }
    /**
     * 给指定的url发送xml报文
     *
     * @param url
     * @param xmlStr
     */
    public String getDataFromUrlWithXml(String url,String xmlStr) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        String rev = null;
        try {
            httpPost.addHeader("charset", "UTF-8");
            HttpEntity httpEntity = null;
            try {
                httpEntity = new StringEntity(xmlStr,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");
            httpPost.setEntity(httpEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            rev =  EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally{	//释放连接
            httpPost.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return rev;
        }
    }
}
