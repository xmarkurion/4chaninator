public class catalogLink {
    private String url;
    private String title;
    private String fullName;
    private int imagesReplyCount;

    public catalogLink(String url, String title, int imagesReplyCount) {
        this.url = url;
        this.title = title;
        this.imagesReplyCount = imagesReplyCount;
        this.fullName = "Images: " + imagesReplyCount + "| T: " + title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String title) {
        this.title = title;
    }

    public void setImagesReplyCount(int imagesReplyCount) {
        this.imagesReplyCount = imagesReplyCount;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public int getImagesReplyCount() {
        return imagesReplyCount;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
