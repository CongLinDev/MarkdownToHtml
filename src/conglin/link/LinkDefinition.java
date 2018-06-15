package conglin.link;

/**
 * 该类负责处理链接问题
 */
public class LinkDefinition {
    private String url;
    private String title;

    /**
     * 构造 LinkDefinition
     * @param url
     * @param title
     */
    public LinkDefinition(String url, String title) {
        this.url = url;
        this.title = title;
    }

    /**
     * 获得LinkDefinition对象的URL
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * 获得LinkDefinition对象的Title
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * LinkDefinition对象转String，格式为   url + " (" + title + ")"
     * @return
     */
    @Override
    public String toString() {
        return url + " (" + title + ")";
    }
}
