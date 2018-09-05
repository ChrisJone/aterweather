package com.runblog.aterweather;

public  interface ApiConstants {
    //天气API
    public static final String weather_server="https://api.heweather.com";
    //地址查询
    public  static final String address_server="https://search.heweather.com";


    enum AddressSearchMode{
        EQUAL("equal","模糊检索"),
        MATCH("match","精准检索");

        private String mode;
        private String desc;

        AddressSearchMode(String mode, String desc) {
            this.mode = mode;
            this.desc = desc;
        }

        public String getMode() {
            return mode;
        }

        public String getDesc() {
            return desc;
        }
    }
    enum AddressGroup{
        WORLD("world","查询全球城市（默认值）"),
        CN("cn","查询中国城市"),
        SCENIC("scenic","中国景点地区"),
        OVERSEAS("overseas","查询海外热门城市（不含中国）");

        private String group;
        private String desc;

        AddressGroup(String group, String desc) {
            this.group = group;
            this.desc = desc;
        }

        public String getGroup() {
            return group;
        }

        public String getDesc() {
            return desc;
        }
    }

    interface AddressApi{
        //城市搜索
        public static final String TOP="/top";
        public static final String FIND="/find";
    }


}
