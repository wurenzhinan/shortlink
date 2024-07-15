package com.nageoffer.shortlink.project.dao.mapper;

import com.nageoffer.shortlink.project.dao.entity.LinkAccessStatsDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * 短链接基础访问持久层
 */
public interface LinkAccessStatsMapper {
    /**
     * 记录基础访问监控数据
     */
    @Insert("INSERT INTO " +
            "t_link_access_stats (gid,full_short_url, date, pv, uv, uip, hour, weekday, create_time, update_time, del_flag) " +
            "VALUES(#{linkAccessStats.gid},#{linkAccessStats.fullShortUrl}, #{linkAccessStats.date}, #{linkAccessStats.pv}, #{linkAccessStats.uv}, #{linkAccessStats.uip}, #{linkAccessStats.hour}, #{linkAccessStats.weekday}, NOW(), NOW(), 0) " +
            "ON DUPLICATE KEY UPDATE pv = pv +  #{linkAccessStats.pv}, uv = uv + #{linkAccessStats.uv}, uip = uip + #{linkAccessStats.uip};")
    void shortLinkStats(@Param("linkAccessStats") LinkAccessStatsDO linkAccessStatsDO);
}
