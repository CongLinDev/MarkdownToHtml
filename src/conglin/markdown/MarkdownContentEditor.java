package conglin.markdown;


/**
 * markdown文本转为该类对象
 * 该类负责提供对文本处理的方法
 */

import conglin.replacement.Replacement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkdownContentEditor {
    private StringBuffer markdownContent;
    public final static int TAB_WIDTH = 4;
    public final static int LESS_TAB_WIDTH = 3;

    /**
     * 构造方法生成对象
     * @param _markdownContent
     */
    public MarkdownContentEditor(String _markdownContent){
        this.markdownContent = new StringBuffer(_markdownContent);
    }
    public MarkdownContentEditor(StringBuffer _markdownContent) {
        this.markdownContent = _markdownContent;
    }
    /**
     * 返回一个String字符串
     * @return
     */
    @Override
    public String toString() {
        return new String(markdownContent);
    }

    /**
     * 利用正则表达式替换对象中所有满足正则表达式的字符串
     * @param regex
     * @param replacementContent
     * @return
     */
    public MarkdownContentEditor replaceAll(String regex, String replacementContent){
        if(this.markdownContent.length()>0){
            Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(markdownContent);

            StringBuffer stringBuffer = new StringBuffer();

            //使用循环将句子里所有的满足的串找出并替换再将内容加到stringBuffer里
            while(matcher.find()){
                matcher.appendReplacement(stringBuffer, replacementContent);
            }
            //最后调用appendTail()方法将最后一次匹配后的剩余字符串加到stringBuffer里
            matcher.appendTail(stringBuffer);

            this.markdownContent = stringBuffer;
        }
        return this;
    }

    /**
     * 利用正则表达式替换对象中所有满足正则表达式的字符串
     * @param pattern
     * @param replacement
     * @return
     */
     public MarkdownContentEditor replaceAll(Pattern pattern, Replacement replacement){
         Matcher matcher = pattern.matcher(markdownContent);

         int lastIndex = 0;
         StringBuffer stringBuffer = new StringBuffer();

         //使用循环将句子里所有的满足的串找出并替换再将内容加到stringBuffer里
         while (matcher.find()){
             stringBuffer.append(markdownContent.subSequence(lastIndex, matcher.start()));
             stringBuffer.append(replacement.replacementString(matcher));
             lastIndex = matcher.end();
         }
         //最后将最后一次匹配后的剩余字符串加到stringBuffer里
         stringBuffer.append(markdownContent.subSequence(lastIndex, markdownContent.length()));

         markdownContent = stringBuffer;

         return this;
     }

     /**
      * 删除正则表达式匹配的字符
      *	@param pattern Regular expression
      * @return
      */
     public MarkdownContentEditor deleteAll(String pattern){
         return this.replaceAll (pattern ,"");
     }

    /**
     * 制表符转空格，默认为4个
     * @param tabWidth  Number of spaces per tab.
     * @return
     */
    public MarkdownContentEditor tabToSpaces(int tabWidth){
        replaceAll(Pattern.compile("(.*?)\\t"), new Replacement() {
            @Override
            public String replacementString(Matcher matcher) {
                String lineSoFar = matcher.group(1);
                int width = lineSoFar.length();
                StringBuffer stringBuffer = new StringBuffer(lineSoFar);
                do{
                    stringBuffer.append(" ");
                }while(width % tabWidth != 0);
                return stringBuffer.toString();
            }
        });
        return this;
    }
     public MarkdownContentEditor tabToSpaces(){
         return tabToSpaces(TAB_WIDTH);
     }

    /**
     * 检测markdownContent是否为空
     * @return
     */
    public boolean isEmpty() {
        return markdownContent.length() == 0;
    }

    /**
     * 在MarkdownContentEditor对象前插入一个字符串
     * @param string
     * @return
     */
    public MarkdownContentEditor prepend(String string) {
        markdownContent.insert(0, string);
        return this;
    }

    /**
     * 在MarkdownContentEditor对象追加一个字符串
     * @param string
     * @return
     */
    public MarkdownContentEditor append(String string){
        markdownContent.append(string);
        return this;
    }

    /**
     * 按照正则表达式将一个MarkdownContentEditor对象分成多个MarkdownContentEditor对象，并返回一个MarkdownContentEditor数组
     * @param _markdownContentEditor
     * @param regex
     * @return
     */
    public static MarkdownContentEditor[] split(MarkdownContentEditor _markdownContentEditor, String regex){
        MarkdownContentEditor[] markdownContentEditorArray = null;
        String []stringArray = null;
        if(_markdownContentEditor.isEmpty()){
            markdownContentEditorArray = new MarkdownContentEditor[0];
        }else {
            stringArray = Pattern.compile(regex).split(_markdownContentEditor.toString());
            markdownContentEditorArray = new MarkdownContentEditor[stringArray.length];
            for(int i = 0; i < stringArray.length; i++){
                markdownContentEditorArray[i] = new MarkdownContentEditor(stringArray[i]);
            }
        }
        return markdownContentEditorArray;
    }

    /**
     * 将MarkdownContentEditor数组以特定字符连接起来生成一个新的MarkdownContentEditor对象
     * @param markdownContentEditorArray
     * @param string
     * @return
     */
    public static MarkdownContentEditor join(MarkdownContentEditor[] markdownContentEditorArray,String string){
        StringBuffer stringBuffer = new StringBuffer();
        for(int i = 0; i < markdownContentEditorArray.length; i++){
            stringBuffer.append(markdownContentEditorArray[i].markdownContent).append(string);
        }
        return new MarkdownContentEditor(stringBuffer);
    }


}
