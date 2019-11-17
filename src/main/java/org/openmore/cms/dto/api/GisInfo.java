package org.openmore.cms.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by michaeltang on 2018/8/9.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GisInfo {

    public static class Point{
        public double lng;
        public double lat;

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }

    @ApiModelProperty(value = "地理信息携带的id数据")
    public String id;

    @ApiModelProperty(value = "地理标记物类名")
    public String clazzName;

    @ApiModelProperty(value = "地图放大倍数")
    public int zoom;

    @ApiModelProperty(value = "中心点")
    public Point center;

    @ApiModelProperty(value = "标记文本，1个汉字/英文字母")
    public String markText;
    @ApiModelProperty(value = "标记的背景颜色")
    public String markBkgColor;

    @ApiModelProperty(value = "区域内背景颜色")
    public String fillColor;
    @ApiModelProperty(value = "区域内背景颜色")
    public double fillOpacity;

    @ApiModelProperty(value = "右键菜单内容，包含id和name")
    public List<Map<String, String>> contextMenu;

    @ApiModelProperty(value = "显示标题")
    public String title;
    @ApiModelProperty(value = "显示标题")
    public Integer titleSize;
    @ApiModelProperty(value = "标题颜色#RRGGBBAA")
    public String titleColor;
    @ApiModelProperty(value = "标题位置")
    public Point titlePosition;

    @ApiModelProperty(value = "多边形边颜色#RRGGBBAA")
    public String lineColor;
    @ApiModelProperty(value = "多边形边样式")
    public String lineStyle;
    @ApiModelProperty(value = "边宽，默认为2")
    public Integer lineWidth = 2;

    @ApiModelProperty(value = "多边形，坐标点数组，x表示经度，y表示纬度")
    public List<Point> polygon;

    public Point getTitlePosition() {
        return titlePosition;
    }

    public void setTitlePosition(Point titlePosition) {
        this.titlePosition = titlePosition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Map<String, String>> getContextMenu() {
        return contextMenu;
    }

    public void setContextMenu(List<Map<String, String>> contextMenu) {
        this.contextMenu = contextMenu;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public List<Point> getPolygon() {
        return polygon;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public Integer getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(Integer lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    public String getLineColor() {
        return lineColor;
    }

    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }

    public void setPolygon(List<Point> polygon) {
        this.polygon = polygon;
    }

    public String getMarkText() {
        return markText;
    }

    public void setMarkText(String markText) {
        this.markText = markText;
    }

    public String getMarkBkgColor() {
        return markBkgColor;
    }

    public String getFillColor() {
        return fillColor;
    }

    public double getFillOpacity() {
        return fillOpacity;
    }

    public void setFillOpacity(double fillOpacity) {
        this.fillOpacity = fillOpacity;
    }

    public String getLineStyle() {
        return lineStyle;
    }

    public void setLineStyle(String lineStyle) {
        this.lineStyle = lineStyle;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    public Integer getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(Integer titleSize) {
        this.titleSize = titleSize;
    }

    public void setMarkBkgColor(String markBkgColor) {
        this.markBkgColor = markBkgColor;
    }
}
