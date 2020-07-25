package com.web.service.service.task;

import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import com.web.service.webservice.GetStationAndTimeByStationNameResponse;
import com.web.service.webservice.TrainTimeWebService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import org.w3c.dom.NodeList;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {
    //3.添加定时任务
    @Scheduled(cron = "0 0 23 * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        //webservice 每天10点读取数据
        TrainTimeWebService trainTimeWebService = new TrainTimeWebService();
        GetStationAndTimeByStationNameResponse.GetStationAndTimeByStationNameResult client = trainTimeWebService.getTrainTimeWebServiceSoap().getStationAndTimeByStationName("北京","郑州","");
        List<Object> any = client.getAny();
        if(CollectionUtils.isEmpty(any)){
            return;
        }
        ElementNSImpl doc = (ElementNSImpl) any.get(1);
        NodeList trainCodes = doc.getElementsByTagName("TrainCode");
        for (int i = 0; i < trainCodes.getLength(); i++) {
            System.out.println("车次:"+trainCodes.item(i).getTextContent());
            System.out.print(doc.getElementsByTagName("FirstStation").item(i).getTextContent()+"--");
            System.out.println(doc.getElementsByTagName("LastStation").item(i).getTextContent());
            System.out.println("始发站:"+doc.getElementsByTagName("StartStation").item(i).getTextContent());
            System.out.println("开车时间:"+doc.getElementsByTagName("StartTime").item(i).getTextContent());
            System.out.println("到达站:"+doc.getElementsByTagName("ArriveStation").item(i).getTextContent());
            System.out.println("到达时间:"+doc.getElementsByTagName("ArriveTime").item(i).getTextContent());
            System.out.println(doc.getElementsByTagName("KM").item(i).getTextContent());
            System.out.println(doc.getElementsByTagName("UseDate").item(i).getTextContent());
            System.out.println("\n\n");
        }


        //httpclient
        try{
            HttpClient httpClient = HttpClientBuilder.create().build();
            // 实例化 HTTP 方法
            String url ="https://www.sogou.com/web?query=java";
            url = url.replace("{", "%7B").replace("}", "%7D").replace("\"", "%22");
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = httpClient.execute(request);
            int code = response.getStatusLine().getStatusCode();
            if (code != HttpStatus.OK.value()) {
                log.error("调用 接口失败:{}", EntityUtils.toString(response.getEntity()));
            }
            String result = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
