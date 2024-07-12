package com.nageoffer.shortlink.project.service.impl;

import com.nageoffer.shortlink.project.service.UrlTitleService;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

/**
 * URL标题实现接口层
 * 类描述： UrlTitleServiceImpl
 **/
@Service
public class UrlTitleServiceImpl implements UrlTitleService {
    /**
     * 根据URL获取标题
     * @param url
     * @return
     */
    @SneakyThrows
    @Override
    public String getTitleByUrl(String url) {
        Document doc = Jsoup.connect(url).get();
        return doc.title();
//        URL tagetUrl = new URL(url);
//        HttpURLConnection connection = (HttpURLConnection) tagetUrl.openConnection();
//        connection.setRequestMethod("GET");
//        connection.connect();
//
//        int responseCode = connection.getResponseCode();
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            Document document = Jsoup.connect(url).get();
//            return document.title();
//        }
//
//        return "Erro while fetching title";
    }
}