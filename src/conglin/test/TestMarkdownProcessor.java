package conglin.test;

import conglin.markdown.MarkdownProcessor;

import java.io.*;
import java.util.Scanner;

public class TestMarkdownProcessor {
    public static void main(String[] args) {
        try(Scanner scan = new Scanner(System.in)) {    //从标准输入流中读取信息
            System.out.print("Please enter the name of source file: ");
            String sourceFileName = scan.next();

            //针对Windows的目录结构进行修改
            sourceFileName.replaceAll("\\\\", "\\\\\\\\");
            process(sourceFileName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void process(String sourceFileName) throws FileNotFoundException {
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
    }
}
