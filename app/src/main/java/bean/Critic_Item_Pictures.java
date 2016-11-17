package bean;

/**
 * Created by 暗示语 on 2016/11/16.
 */

public class Critic_Item_Pictures {

    /**
     * id : 100097
     * title : 喵星人占领世界
     * author :  Ms. Cat
     * authorbrief : Cute illustrations by Ms. Cat
     * text1 : 世界那么大，我却偏偏遇见你；世界那么小，我却偏偏丢了你。

     世界那么大，我却总是无法忘记你；世界那么小，我却总是无法遇见你。

     * image1 : images/69B6CE641E6E385B3DD05F8957FA6A40.jpg
     * text2 : —《那些回不去的年少时光》
     * times : 4453
     * publishtime : 636148512000000000
     * status : 0
     * errMsg : null
     */

    private String title;
    private String authorbrief;
    private String text1;
    private String image1;
    private String text2;
    private int times;
    private long publishtime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorbrief() {
        return authorbrief;
    }

    public void setAuthorbrief(String authorbrief) {
        this.authorbrief = authorbrief;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public long getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(long publishtime) {
        this.publishtime = publishtime;
    }
}
