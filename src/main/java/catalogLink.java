public class catalogLink {
    private String url;
    private String name;
    private int imagesReplyCount;

    public catalogLink(String url, String name, int imagesReplyCount) {
        this.url = url;
        this.name = name;
        this.imagesReplyCount = imagesReplyCount;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImagesReplyCount(int imagesReplyCount) {
        this.imagesReplyCount = imagesReplyCount;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public int getImagesReplyCount() {
        return imagesReplyCount;
    }
}
