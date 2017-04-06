package com.happysong.app.bean;

import java.util.List;

/**
 * Created by relicemxd on 16/1/21.
 * http://api.1-blog.com/biz/bizserver/xiaohua/
 * list.do?maxXhid=1000000&size=1&minXhid=6&page=1
 * 笑话数据信息bean
 */
public class SmileInfo extends JsonBean {

    /**
     * status : 000000
     * desc : null
     * detail : [{"id":37871,"xhid":37871,
     * "author":"矮穷挫注孤生",
     * "content":"跑步真的不能穿宽松内裤，搞得我都无法控制身体重心了",
     * "picUrl":"","status":"1"}]
     */

    private String status;
    private Object desc;
    /**
     * id : 37871
     * xhid : 37871
     * author : 矮穷挫注孤生
     * content : 跑步真的不能穿宽松内裤，搞得我都无法控制身体重心了
     * picUrl :
     * status : 1
     */

    private List<DetailEntity> detail;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDesc(Object desc) {
        this.desc = desc;
    }

    public void setDetail(List<DetailEntity> detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public Object getDesc() {
        return desc;
    }

    public List<DetailEntity> getDetail() {
        return detail;
    }

    public static class DetailEntity {
        private int id;
        private int xhid;
        private String author;
        private String content;
        private String picUrl;
        private String status;

        public void setId(int id) {
            this.id = id;
        }

        public void setXhid(int xhid) {
            this.xhid = xhid;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public int getXhid() {
            return xhid;
        }

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public String getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return "DetailEntity{" +
                    "id=" + id +
                    ", xhid=" + xhid +
                    ", author='" + author + '\'' +
                    ", content='" + content + '\'' +
                    ", picUrl='" + picUrl + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SmileInfo{" +
                "status='" + status + '\'' +
                ", desc=" + desc +
                ", detail=" + detail +
                '}';
    }
}
