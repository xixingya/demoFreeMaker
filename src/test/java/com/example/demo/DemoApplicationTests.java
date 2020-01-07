package com.example.demo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("num1",1);
        Configuration configuration=new Configuration();

        Template t2 = configuration.getTemplate("template.ftl"); // freeMarker template
        String content2 = FreeMarkerTemplateUtils.processTemplateIntoString(t2, model);
        int widthImage = 1600;
        int heightImage = 1000;
        String inputFileName = "source.xhtml";
        String outFileName = "image.jpg";
        File image=XhtmlToImageUtils.convertXhtmlToImage(content2,inputFileName,outFileName,widthImage,heightImage);

    }

    @Test
    private void exportWord() {
        Base64Encoder encoder=new Base64Encoder();
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream("D:\\1.jpg");
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String image64Str=new String(Base64.encodeBase64(data));
        Map<String, Object> dataMap = new HashMap<String, Object>();

        //image64Str 是职员的照片转换后的BASE64字符串
        dataMap.put("images", image64Str);
        //stdName 是我前面获取的职员信息



        /*//expList 是职员的工作经历信息的集合，对应模板中的表格
        List<Map<String, Object>> expList = new ArrayList<Map<String, Object>>();
        Iterator<Map.Entry<String, String>> it = maps.entrySet().iterator();
        while (it.hasNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            Map.Entry<String, String> entity = (Map.Entry<String, String>) it.next();
            map.put("time", entity.getKey());
            map.put("experience", entity.getValue());
            expList.add(map);
        }
        dataMap.put("expList", expList);*/

        //@SuppressWarnings("deprecation")
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        //有两种方式获取你的模板，模板在项目中时用第一个，模板在本地时用第二个。
        //注意：两种方式的路径都只需要写到模板的上一级目录
        configuration.setClassForTemplateLoading(this.getClass(), "/template");
//  configuration.setDirectoryForTemplateLoading(new File("C:/"));

        File outFile = new File("D:/outFilessa"+Math.random()*10000+".doc");//输出路径
        Template t=null;
        Writer out = null;
        try {
            t = configuration.getTemplate("template.ftl", "utf-8"); //文件名，获取模板
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
            t.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException  e) {
                e.printStackTrace();
            }
        }

    }

}
