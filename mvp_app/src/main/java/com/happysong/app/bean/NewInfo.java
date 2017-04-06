package com.happysong.app.bean;

import java.util.List;

/**
 * @Package: com.example.relicemxd.happysong.bean
 * @Author: Relice
 * @Date: 16/4/25
 * @Des: 新闻信息
 */
public class NewInfo extends JsonBean {


    /**
     * status : 000000
     * desc : null
     * detail : [{"title":"习近平走进大别山：全面小康不能少了老区","source":"手机央广网","article_url":"http://m.cnr.cn/jdt/ttyc/20160425/t20160425_521969560_tt.html","publish_time":0,"behot_time":1461547800000,"create_time":0,"digg_count":0,"bury_count":0,"repin_count":5,"group_id":"6277150840402739714"},{"title":"韩国杀人加湿器杀菌剂致239死 民众集体愤怒","source":"中国青年网","article_url":"http://toutiao.com/preview_article/?pgc_id=6277285669190500866","publish_time":0,"behot_time":1461544981000,"create_time":0,"digg_count":0,"bury_count":0,"repin_count":1,"group_id":"6277157139169411586"},{"title":"与壳公司老板洽谈须交千万见面费","source":"中金在线","article_url":"http://3g.cnfol.com/sc_stock/201604/22632576.shtml","publish_time":0,"behot_time":1461541573000,"create_time":0,"digg_count":0,"bury_count":0,"repin_count":27,"group_id":"6277211259444740354"},{"title":"中国的朋友圈在不断扩大 我国南海立场获文柬老支持","source":"环球网","article_url":"http://w.huanqiu.com/r/MV8wXzg4MjA2OTlfMTM4XzE0NjE1MjgwMDA=","publish_time":0,"behot_time":1461541407000,"create_time":0,"digg_count":0,"bury_count":0,"repin_count":1,"group_id":"6277250196032848129"},{"title":"姚晨宣布怀二胎 前夫凌潇肃隔空与唐一菲秀恩爱","source":"光明网","article_url":"http://m.gmw.cn/toutiao/2016-04/25/content_19835244.htm","publish_time":0,"behot_time":1461541252000,"create_time":0,"digg_count":0,"bury_count":0,"repin_count":0,"group_id":"6277160260852728066"},{"title":"第一关注：全国性高考加分项目只留5项","source":"看看新闻","article_url":"http://domhttp.kksmg.com/2016/04/25/h264_450k_mp4_LiaoNingHD25000002016042518787064091_aac.mp4","publish_time":0,"behot_time":1461540635000,"create_time":0,"digg_count":1,"bury_count":0,"repin_count":0,"group_id":"6277255035118190850"},{"title":"25省已公布国企限薪令 老总年薪最多为职工8倍","source":"中国青年网","article_url":"http://t.m.youth.cn/transfer/toutiao/url/news.youth.cn/gn/201604/t20160425_7906006.htm","publish_time":0,"behot_time":1461540438000,"create_time":0,"digg_count":0,"bury_count":0,"repin_count":0,"group_id":"6277177793839202562"},{"title":"战报：阿森纳重回第四 莱斯特城4-0 尤文2-1最快今日夺冠","source":"足球部落","article_url":"http://toutiao.com/preview_article/?pgc_id=6277262417848173058","publish_time":0,"behot_time":1461540100000,"create_time":0,"digg_count":1,"bury_count":0,"repin_count":46,"group_id":"6277257305214353666"},{"title":"网曝一00后女孩即将生孩子","source":"手机央广网","article_url":"http://m.cnr.cn/jdt/ttyc/20160425/t20160425_521969547_tt.html","publish_time":0,"behot_time":1461539923000,"create_time":0,"digg_count":0,"bury_count":16,"repin_count":192,"group_id":"6277125610835017986"},{"title":"高校保安撞狗护学生 爱狗人士校门口示威","source":"光明网","article_url":"http://m.gmw.cn/gallery/201604/25/19835717.html","publish_time":0,"behot_time":1461539532000,"create_time":0,"digg_count":0,"bury_count":9,"repin_count":34,"group_id":"6277129697655587074"}]
     */

    private String status;
    private Object desc;
    /**
     * title : 习近平走进大别山：全面小康不能少了老区
     * source : 手机央广网
     * article_url : http://m.cnr.cn/jdt/ttyc/20160425/t20160425_521969560_tt.html
     * publish_time : 0
     * behot_time : 1461547800000
     * create_time : 0
     * digg_count : 0
     * bury_count : 0
     * repin_count : 5
     * group_id : 6277150840402739714
     */

    private List<DetailEntity> detail;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getDesc() {
        return desc;
    }

    public void setDesc(Object desc) {
        this.desc = desc;
    }

    public List<DetailEntity> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailEntity> detail) {
        this.detail = detail;
    }

    public static class DetailEntity {
        private String title;
        private String source;
        private String article_url;
        private int publish_time;
        private long behot_time;
        private int create_time;
        private int digg_count;
        private int bury_count;
        private int repin_count;
        private String group_id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getArticle_url() {
            return article_url;
        }

        public void setArticle_url(String article_url) {
            this.article_url = article_url;
        }

        public int getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(int publish_time) {
            this.publish_time = publish_time;
        }

        public long getBehot_time() {
            return behot_time;
        }

        public void setBehot_time(long behot_time) {
            this.behot_time = behot_time;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getDigg_count() {
            return digg_count;
        }

        public void setDigg_count(int digg_count) {
            this.digg_count = digg_count;
        }

        public int getBury_count() {
            return bury_count;
        }

        public void setBury_count(int bury_count) {
            this.bury_count = bury_count;
        }

        public int getRepin_count() {
            return repin_count;
        }

        public void setRepin_count(int repin_count) {
            this.repin_count = repin_count;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        @Override
        public String toString() {
            return "DetailEntity{" +
                    "title='" + title + '\'' +
                    ", source='" + source + '\'' +
                    ", article_url='" + article_url + '\'' +
                    ", publish_time=" + publish_time +
                    ", behot_time=" + behot_time +
                    ", create_time=" + create_time +
                    ", digg_count=" + digg_count +
                    ", bury_count=" + bury_count +
                    ", repin_count=" + repin_count +
                    ", group_id='" + group_id + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewInfo{" +
                "status='" + status + '\'' +
                ", desc=" + desc +
                ", detail=" + detail +
                '}';
    }
}
