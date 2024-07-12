package com.nageoffer.shortlink.project.service;

/**
 * URL标题接口层
 */
public interface UrlTitleService {
    /**
     * 根据URL获取标题
     * @param url
     * @return
     */
    String getTitleByUrl(String url);
}
