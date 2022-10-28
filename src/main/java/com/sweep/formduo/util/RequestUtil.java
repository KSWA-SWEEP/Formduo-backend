package com.sweep.formduo.util;
import com.sweep.formduo.web.dto.survey_resps.ConvResDto;
import org.codehaus.jackson.map.util.ObjectBuffer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class RequestUtil {


    public static ConvResDto restRequest(String msg) throws ParseException {

        String api_key = "4c40dd27da9e877f7df64b6d77df572b";
        String end_url = "https://a3d8fbea-c67e-4cac-8e14-f0af2ee1671f.api.kr-central-1.kakaoi.io/ai/conversation/a170a37cbdfd45b5883c82cf4552e324";
        String result = "";
        //보낼 파라메터 셋팅
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        HashMap<String, String> params = new HashMap<>();
        params.put("msg", msg);
//        params.add("msg", msg);

        //헤더셋팅
        HttpHeaders headers = new HttpHeaders();
//        headers.add("accept", "text/plain;charset=UTF-8");
        headers.add("Content-Type", "application/json");
        headers.add("x-api-key", api_key);


        //파라메터와 헤어 합치기
        HttpEntity<HashMap<String, String>> entity = new HttpEntity<>(params, headers);
        //RestTemplate 초기화
        RestTemplate rt = new RestTemplate();

        //전송 및 결과 처리
        ResponseEntity<String> response = rt.exchange(
                end_url,
                HttpMethod.POST,
                entity,
                String.class
        );
        result = response.getBody();//리턴되는 결과의 body를 저장.

        // body 정보들을 json으로 변환
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(result);

        HashMap<String, Integer> rs = new HashMap<>();

        // body의 key들을 iterator로 변환
        Iterator<String> itr = obj.keySet().iterator();

        // 모든 key값에 대한 emotion을 확인한다.
        while(itr.hasNext())
        {
            String tmp = itr.next().toString();
            if (tmp.equals("release")) break;
            JSONObject object = (JSONObject) parser.parse(obj.get(tmp).toString());
            JSONObject object1 = (JSONObject) parser.parse(object.get("emotion").toString());

            // 같은 값이 있을 경우 value 1 증가
            if ( rs.containsKey(object1.get("value").toString()) ){
                rs.put(object1.get("value").toString(), rs.get(object1.get("value").toString())+1);
            }
            // 값은 값이 없을 경우 value 1로 설정
            else {
                rs.put(object1.get("value").toString(), 1);
            }
        }

        // emotion들의 최빈값을 도출하기 위한 Comparator 생성
        Comparator<Map.Entry<String, Integer>> comparator = new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2)
            {
                return e1.getValue().compareTo(e2.getValue());
            }
        };

        // value 기반으로 최댁값 도출
        Map.Entry<String, Integer> maxEntry = Collections.max(rs.entrySet(), comparator);
//        System.out.println(maxEntry);

        return new ConvResDto(maxEntry.getKey(), maxEntry.getValue());
    }
}