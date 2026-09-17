package com.example.demo.car;

import com.example.demo.car.AuthService;
import com.example.demo.util.HttpUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;

@RestController
@RequestMapping("/car")
@CrossOrigin
public class carController {

    @PostMapping(value = "/car")
    @ApiOperation(value = "车辆识别", notes = "车辆识别")
    public car plant(@RequestParam("file") MultipartFile file) {
        //车牌识别地址
        String chepaiurl = "https://aip.baidubce.com/rest/2.0/ocr/v1/license_plate";
        //车型识别地址
        String chexingurl = "https://aip.baidubce.com/rest/2.0/image-classify/v1/car";
        String number = "";

        car car = new car();
        try {
            byte[] imgData = file.getBytes();
            String imgStr = Base64.getEncoder().encodeToString(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String paramOne = "image=" + imgParam;
            String paramTwo = "image=" + imgParam + "&top_num=" + 5;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间，客户需自行存储，定期刷新
            String accessToken = AuthService.getAuth();

            //车牌号识别
            String chepai = HttpUtil.post(chepaiurl, accessToken, "application/json", paramOne);
            JSONObject chepaiObj = new JSONObject(chepai);
            number = chepaiObj.getJSONObject("words_result")
                    .getString("number");

            car.setChepai(number);

            //车型识别
            String chexing = HttpUtil.post(chexingurl, accessToken, "application/json", paramTwo);

            // 获取第一个结果的name和year
            JSONObject jsonObject = new JSONObject(chexing);
            JSONArray resultArray = jsonObject.getJSONArray("result");

            // 获取第一个结果（分数最高的）
            if (resultArray.length() > 0) {
                JSONObject firstResult = resultArray.getJSONObject(0);
                String name = firstResult.getString("name");
                String year = firstResult.getString("year");
                car.setYear(year);
                car.setName(name);
            }

            return car;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return car;
    }
}