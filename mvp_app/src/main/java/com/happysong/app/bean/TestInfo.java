package com.happysong.app.bean;

import java.util.List;

/**
 * http://www.pifayanjing.com/?m=app&do=index_json
 */
public class TestInfo extends JsonBean {

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * pinpai_aid : 77
         * img : http://images.51yanjing.com/upload/images/resource/48/0820533691.jpg
         * pinpai_cover : http://images.51yanjing.com/upload/images/pinpai_logo/77_50646254.jpg
         * pinpai_title : 明月光学
         */

        private List<HeaderPosterBean> header_poster;
        /**
         * pinpai_pinpaiid : 30
         * pinpai_title : 雷朋
         * pinpai_cover : http://images.51yanjing.com/upload/images/pinpai_logo/30_898e6.jpg?r_
         */

        private List<HotPinpaiBean> hot_pinpai;
        /**
         * pinpai_aid : 30
         * pinpai_title : Ray-Ban 雷朋
         * p_logo_url : http://images.51yanjing.com/upload/images/pinpai_logo/30_898e6.jpg
         * pinpai_cover : http://img.51yanjing.com/upload/images/resource/50/115.jpg
         */

        private List<NewPinpaiBean> new_pinpai;
        /**
         * sorts_title : 光学镜架
         * page_key : guangxuejingjia
         * pinpai_data : [{"pinpai_aid":58,"p_logo_url":"http://images.51yanjing.com/upload/images/pinpai_logo/58_06425.jpg","pinpai_cover":"http://images.51yanjing.com/upload/images/resource/51/122.jpg"},{"pinpai_aid":29,"p_logo_url":"http://images.51yanjing.com/upload/images/pinpai_logo/29_c132e.jpg","pinpai_cover":"http://images.51yanjing.com/upload/images/resource/51/121.jpg"},{"pinpai_aid":59,"p_logo_url":"http://images.51yanjing.com/upload/images/pinpai_logo/59_09919.jpg","pinpai_cover":"http://images.51yanjing.com/upload/images/resource/51/120.jpg"},{"pinpai_aid":38,"p_logo_url":"http://images.51yanjing.com/upload/images/pinpai_logo/38_f7d7f.jpg","pinpai_cover":"http://images.51yanjing.com/upload/images/resource/51/119.jpg"},{"pinpai_aid":31,"p_logo_url":"http://images.51yanjing.com/upload/images/pinpai_logo/31_6b346.jpg","pinpai_cover":"http://images.51yanjing.com/upload/images/resource/51/118.jpg"},{"pinpai_aid":59,"p_logo_url":"http://images.51yanjing.com/upload/images/pinpai_logo/59_09919.jpg","pinpai_cover":"http://images.51yanjing.com/upload/images/resource/51/1.jpg"}]
         */

        private List<PinpaiSortsBean> pinpai_sorts;

        public List<HeaderPosterBean> getHeader_poster() {
            return header_poster;
        }

        public void setHeader_poster(List<HeaderPosterBean> header_poster) {
            this.header_poster = header_poster;
        }

        public List<HotPinpaiBean> getHot_pinpai() {
            return hot_pinpai;
        }

        public void setHot_pinpai(List<HotPinpaiBean> hot_pinpai) {
            this.hot_pinpai = hot_pinpai;
        }

        public List<NewPinpaiBean> getNew_pinpai() {
            return new_pinpai;
        }

        public void setNew_pinpai(List<NewPinpaiBean> new_pinpai) {
            this.new_pinpai = new_pinpai;
        }

        public List<PinpaiSortsBean> getPinpai_sorts() {
            return pinpai_sorts;
        }

        public void setPinpai_sorts(List<PinpaiSortsBean> pinpai_sorts) {
            this.pinpai_sorts = pinpai_sorts;
        }

        public static class HeaderPosterBean {
            private int pinpai_aid;
            private String img;
            private String pinpai_cover;
            private String pinpai_title;

            public int getPinpai_aid() {
                return pinpai_aid;
            }

            public void setPinpai_aid(int pinpai_aid) {
                this.pinpai_aid = pinpai_aid;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getPinpai_cover() {
                return pinpai_cover;
            }

            public void setPinpai_cover(String pinpai_cover) {
                this.pinpai_cover = pinpai_cover;
            }

            public String getPinpai_title() {
                return pinpai_title;
            }

            public void setPinpai_title(String pinpai_title) {
                this.pinpai_title = pinpai_title;
            }
        }

        public static class HotPinpaiBean {
            private int pinpai_pinpaiid;
            private String pinpai_title;
            private String pinpai_cover;

            public int getPinpai_pinpaiid() {
                return pinpai_pinpaiid;
            }

            public void setPinpai_pinpaiid(int pinpai_pinpaiid) {
                this.pinpai_pinpaiid = pinpai_pinpaiid;
            }

            public String getPinpai_title() {
                return pinpai_title;
            }

            public void setPinpai_title(String pinpai_title) {
                this.pinpai_title = pinpai_title;
            }

            public String getPinpai_cover() {
                return pinpai_cover;
            }

            public void setPinpai_cover(String pinpai_cover) {
                this.pinpai_cover = pinpai_cover;
            }
        }

        public static class NewPinpaiBean {
            private int pinpai_aid;
            private String pinpai_title;
            private String p_logo_url;
            private String pinpai_cover;

            public int getPinpai_aid() {
                return pinpai_aid;
            }

            public void setPinpai_aid(int pinpai_aid) {
                this.pinpai_aid = pinpai_aid;
            }

            public String getPinpai_title() {
                return pinpai_title;
            }

            public void setPinpai_title(String pinpai_title) {
                this.pinpai_title = pinpai_title;
            }

            public String getP_logo_url() {
                return p_logo_url;
            }

            public void setP_logo_url(String p_logo_url) {
                this.p_logo_url = p_logo_url;
            }

            public String getPinpai_cover() {
                return pinpai_cover;
            }

            public void setPinpai_cover(String pinpai_cover) {
                this.pinpai_cover = pinpai_cover;
            }
        }

        public static class PinpaiSortsBean {
            private String sorts_title;
            private String page_key;
            /**
             * pinpai_aid : 58
             * p_logo_url : http://images.51yanjing.com/upload/images/pinpai_logo/58_06425.jpg
             * pinpai_cover : http://images.51yanjing.com/upload/images/resource/51/122.jpg
             */

            private List<PinpaiDataBean> pinpai_data;

            public String getSorts_title() {
                return sorts_title;
            }

            public void setSorts_title(String sorts_title) {
                this.sorts_title = sorts_title;
            }

            public String getPage_key() {
                return page_key;
            }

            public void setPage_key(String page_key) {
                this.page_key = page_key;
            }

            public List<PinpaiDataBean> getPinpai_data() {
                return pinpai_data;
            }

            public void setPinpai_data(List<PinpaiDataBean> pinpai_data) {
                this.pinpai_data = pinpai_data;
            }

            public static class PinpaiDataBean {
                private int pinpai_aid;
                private String p_logo_url;
                private String pinpai_cover;

                public int getPinpai_aid() {
                    return pinpai_aid;
                }

                public void setPinpai_aid(int pinpai_aid) {
                    this.pinpai_aid = pinpai_aid;
                }

                public String getP_logo_url() {
                    return p_logo_url;
                }

                public void setP_logo_url(String p_logo_url) {
                    this.p_logo_url = p_logo_url;
                }

                public String getPinpai_cover() {
                    return pinpai_cover;
                }

                public void setPinpai_cover(String pinpai_cover) {
                    this.pinpai_cover = pinpai_cover;
                }
            }
        }
    }
}
