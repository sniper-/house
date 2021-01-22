package com.house.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Baidu 地图转坐标 Geocoding API
 */
public class BaiduUtils {

    private static Logger logger = LoggerFactory.getLogger(BaiduUtils.class);

    private static final String APP_KEY = "HNkLQGGAP56uGm6GiiCammbeFM8btKLG";


    public static void main(String args[]) {
        Location location = BaiduUtils.getLatitude("福建省福州市闽侯县福州大学");
        System.out.println("经度" + location.getLongitude() + ",纬度" + location.getLatitude());
    }

    /**
     * 输入中文地址, 正常返回格式为:
     * {"status":"OK","result":{"location":{"lng":119.243461,"lat":26.088571},"precise":1,"confidence":70,"level":"\u6559\u80b2"}}
     *
     * @param address 中文地址
     * @return lng(经度), lat(纬度)
     */
    public static Location getLatitude(String address) {

        String res;

        String bdResponse = null;

        try {
            // 将地址转换成utf-8的16进制
            address = URLEncoder.encode(address, "UTF-8");
            URL url = new URL("http://api.map.baidu.com/geocoder?address=" + address + "&output=json&key=" + APP_KEY);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();
            while ((res = in.readLine()) != null) {
                sb.append(res.trim());
            }
            in.close();
            bdResponse = sb.toString();
            if (!StringUtils.isEmpty(bdResponse)) {
                //返回经纬度
                JSONObject response = JSONObject.parseObject(bdResponse);
                JSONObject result = JSONObject.parseObject(response.get("result").toString());
                JSONObject location = JSONObject.parseObject(result.get("location").toString());
                Location locationObj = new Location();
                locationObj.setLongitude(location.get("lng") == null ? null : location.get("lng").toString());
                locationObj.setLatitude(location.get("lat") == null ? null : location.get("lat").toString());
                return locationObj;
            }
        } catch (Exception e) {
            logger.error("地址解析出现异常！ URL返回信息={}, e={}", bdResponse, e);
        }
        return null;
    }

    /**
     * 经纬度对象
     */
    public static class Location {

        /**
         * 经度
         */
        private String longitude;

        /**
         * 纬度
         */
        private String Latitude;

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return Latitude;
        }

        public void setLatitude(String latitude) {
            Latitude = latitude;
        }
    }
}