package conglin.replacement;

import java.util.regex.Matcher;

public interface Replacement {
    String replacementString(Matcher matcher);
//    @Override
//    String toString(){
//        return this.replacementString(matcher);
//    }
}
