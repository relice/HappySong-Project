package com.happysong.app.bean;

import java.util.List;

/**
 * http://route.showapi.com/255-1?showapi_appid=31249&
 * showapi_sign=137df2f5f9f046fa96703a0578fbe098
 * 百思不得姐信息
 */
public class BsbdqjInfo extends JsonBean {


    private int showapi_res_code;
    private String showapi_res_error;

    private ShowapiResBodyBean showapi_res_body;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean {
        private int ret_code;

        private PagebeanBean pagebean;

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public PagebeanBean getPagebean() {
            return pagebean;
        }

        public void setPagebean(PagebeanBean pagebean) {
            this.pagebean = pagebean;
        }

        public static class PagebeanBean {
            private int allPages;
            private int currentPage;
            private int allNum;
            private int maxResult;
            /**
             * text :  耳朵什么的最敏感了，你身边有这样的妹子吗？
             * hate : 59
             * videotime : 0
             * voicetime : 0
             * weixin_url : http://m.budejie.com/detail-23435610.html/
             * profile_image : http://qzapp.qlogo.cn/qzapp/100336987/A68E21CDB3B68D059BCB68B05F1BB166/100
             * width : 0
             * voiceuri :
             * type : 10
             * image0 : http://wimg.spriteapp.cn/ugc/2017/02/01/5891ff86acdca.gif
             * id : 23435610
             * love : 72
             * image2 : http://wimg.spriteapp.cn/ugc/2017/02/01/5891ff86acdca.gif
             * image1 : http://wimg.spriteapp.cn/ugc/2017/02/01/5891ff86acdca.gif
             * height : 0
             * name : 纯δ勿澀777
             * create_time : 2017-02-02 10:38:02
             * image3 : http://wimg.spriteapp.cn/ugc/2017/02/01/5891ff86acdca.gif
             */

            private List<ContentlistBean> contentlist;

            public int getAllPages() {
                return allPages;
            }

            public void setAllPages(int allPages) {
                this.allPages = allPages;
            }

            public int getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }

            public int getAllNum() {
                return allNum;
            }

            public void setAllNum(int allNum) {
                this.allNum = allNum;
            }

            public int getMaxResult() {
                return maxResult;
            }

            public void setMaxResult(int maxResult) {
                this.maxResult = maxResult;
            }

            public List<ContentlistBean> getContentlist() {
                return contentlist;
            }

            public void setContentlist(List<ContentlistBean> contentlist) {
                this.contentlist = contentlist;
            }

            public static class ContentlistBean {
                private String text;
                private String hate;
                private String videotime;
                private String voicetime;
                private String weixin_url;
                private String profile_image;
                private String width;
                private String voiceuri;
                private String video_uri;
                private String type;
                private String image0;
                private String id;
                private String love;
                private String image2;
                private String image1;
                private String height;
                private String name;
                private String create_time;
                private String image3;

                @Override
                public String toString() {
                    return "ContentlistBean{" +
                            "text='" + text + '\'' +
                            ", hate='" + hate + '\'' +
                            ", videotime='" + videotime + '\'' +
                            ", voicetime='" + voicetime + '\'' +
                            ", weixin_url='" + weixin_url + '\'' +
                            ", profile_image='" + profile_image + '\'' +
                            ", width='" + width + '\'' +
                            ", voiceuri='" + voiceuri + '\'' +
                            ", type='" + type + '\'' +
                            ", image0='" + image0 + '\'' +
                            ", id='" + id + '\'' +
                            ", love='" + love + '\'' +
                            ", image2='" + image2 + '\'' +
                            ", image1='" + image1 + '\'' +
                            ", height='" + height + '\'' +
                            ", name='" + name + '\'' +
                            ", create_time='" + create_time + '\'' +
                            ", image3='" + image3 + '\'' +
                            '}';
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getVideo_uri() {
                    return video_uri;
                }

                public void setVideo_uri(String video_uri) {
                    this.video_uri = video_uri;
                }

                public String getHate() {
                    return hate;
                }

                public void setHate(String hate) {
                    this.hate = hate;
                }

                public String getVideotime() {
                    return videotime;
                }

                public void setVideotime(String videotime) {
                    this.videotime = videotime;
                }

                public String getVoicetime() {
                    return voicetime;
                }

                public void setVoicetime(String voicetime) {
                    this.voicetime = voicetime;
                }

                public String getWeixin_url() {
                    return weixin_url;
                }

                public void setWeixin_url(String weixin_url) {
                    this.weixin_url = weixin_url;
                }

                public String getProfile_image() {
                    return profile_image;
                }

                public void setProfile_image(String profile_image) {
                    this.profile_image = profile_image;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getVoiceuri() {
                    return voiceuri;
                }

                public void setVoiceuri(String voiceuri) {
                    this.voiceuri = voiceuri;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getImage0() {
                    return image0;
                }

                public void setImage0(String image0) {
                    this.image0 = image0;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getLove() {
                    return love;
                }

                public void setLove(String love) {
                    this.love = love;
                }

                public String getImage2() {
                    return image2;
                }

                public void setImage2(String image2) {
                    this.image2 = image2;
                }

                public String getImage1() {
                    return image1;
                }

                public void setImage1(String image1) {
                    this.image1 = image1;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }

                public String getImage3() {
                    return image3;
                }

                public void setImage3(String image3) {
                    this.image3 = image3;
                }
            }


        }
    }

    @Override
    public String toString() {
        return "BsbdqjInfo{" +
                "showapi_res_code=" + showapi_res_code +
                ", showapi_res_error='" + showapi_res_error + '\'' +
                ", showapi_res_body=" + showapi_res_body +
                '}';
    }
}
