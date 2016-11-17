package bean;

import java.util.List;

/**
 * Created by 暗示语 on 2016/11/15.
 */

public class CriticBean {

    /**
     * id : 100091
     * type : 1
     * publishtime : 636147648000000000
     * title : 《鬼妈妈》卡萝琳的性启蒙
     * summary : 稍微有点而吓人，胆小慎入
     * image : images/054F341821D4410312181087F9F0C0D7.jpg
     */

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private int id;
        private long publishtime;
        private String title;
        private String summary;
        private String image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getPublishtime() {
            return publishtime;
        }

        public void setPublishtime(long publishtime) {
            this.publishtime = publishtime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
