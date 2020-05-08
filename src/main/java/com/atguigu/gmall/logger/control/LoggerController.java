package com.atguigu.gmall.logger.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @Controller 返回页面
@RestController // 返回字符串值 == @Controller + 方法上得 @ResponseBody
@Slf4j
public class LoggerController {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @RequestMapping("/applog")
    public String applog(@RequestBody String logString){
        // System.out.println(log);
        log.info(logString);

        JSONObject jsonObject = JSON.parseObject(logString);
        if(jsonObject.getString("start") != null && jsonObject.getString("start").length() > 0){
            kafkaTemplate.send("GMALL_START",logString);
        }else {
            kafkaTemplate.send("GMALL_EVENT",logString);
        }

        return "success";
    }

}
