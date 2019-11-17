package org.openmore.cms.dto.api;

/**
 * 民情图统计元数据
 */
public class MetaData {
    /**
     * 户号
     */
    private String house;
    /**
     * 户主信息
     */
    private String houseHolder;
    /**
     * 居民标签
     */
    private String tags;

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getHouseHolder() {
        return houseHolder;
    }

    public void setHouseHolder(String houseHolder) {
        this.houseHolder = houseHolder;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}