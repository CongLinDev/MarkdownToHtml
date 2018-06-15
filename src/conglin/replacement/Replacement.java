package conglin.replacement;

import java.util.regex.Matcher;

/**
 * 一个规范接口
 */
public interface Replacement {
    String replacementString(Matcher matcher);
}
