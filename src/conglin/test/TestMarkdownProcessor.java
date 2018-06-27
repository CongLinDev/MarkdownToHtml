package conglin.test;

import conglin.markdown.MarkdownProcessor;

import java.io.*;
import java.util.Scanner;

/**
 * 测试类，负责测试 MarkdownProcessor
 */
public class TestMarkdownProcessor {
    /**
     * 主方法
     * @param args 主指令
     */
    public static void main(String[] args) {
        try(Scanner scan = new Scanner(System.in)) {    //从标准输入流中读取信息
            System.out.print("Please enter the name of source file: ");
            String sourceFileName = scan.next();

            System.out.println(process(sourceFileName) + " " + "has finished.");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * 检测MarkdownProcessor具体方法
     * @param sourceFileName 源文件名
     * @return 返回目标文件名
     * @throws FileNotFoundException 抛出FileNotFoundException异常
     */
    public static String process(String sourceFileName) throws FileNotFoundException {
        String destinationFileName = sourceFileName + ".html";
        File sourceFile = new File(sourceFileName);
        File destinationFile = new File(destinationFileName);

        if (!sourceFile.exists()) {
            throw new FileNotFoundException();
        }
        if(destinationFile.exists()){
            destinationFile.delete();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(sourceFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(destinationFileName))){
            //源文件写入 sourceFileContent
            StringBuffer sourceFileContent = new StringBuffer();
            String s = null;
            while((s = br.readLine()) != null){
                sourceFileContent.append(System.lineSeparator()+ s);
            }

            MarkdownProcessor md2Html = new MarkdownProcessor(sourceFileContent);
            String htmlFileContent = md2Html.translate();

            // htmlFileContent写入目标文件
            bw.write(htmlFileContent);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return destinationFileName;
    }
}
