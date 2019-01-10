package com.snooker.util;

import com.snooker.exception.InnerException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * Authored by WangJunyu on 2017/4/6
 */
public class JsoupUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsoupUtil.class);

    /**
     * 抽取页面指定数据的通用方法
     *
     * @param url 页面url
     * @param cssQuery css选择器语法
     * @param params 查询参数(可选)
     * @return 元素列表
     */
    public static Elements getElements(String url, String cssQuery, Object... params) {
        Document document = null;
        url = MessageFormat.format(url,params);
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36";
        try {
            document = Jsoup.connect(url).timeout(30000).header("User-Agent", userAgent).get();
        } catch (IOException e) {
            logger.error("爬虫异常", e);
            throw new InnerException(InnerException.SCRAWLER_CONNECT_ERROR);
        }
        if (document == null) {
            logger.error("fetch document is null. {}", url );
            throw new InnerException(InnerException.SCRAWLER_CONNECT_ERROR);
        }
        return document.select(cssQuery);
    }
}
