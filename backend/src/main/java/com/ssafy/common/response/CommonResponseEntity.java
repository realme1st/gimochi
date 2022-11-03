package com.ssafy.common.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommonResponseEntity<T>  extends BasicResponse {
    /*
    * api 요청 성공시엔 반환하고자 하는 데이터를 담은 T data, 데이터의 길이인 int count를 반환한다.
    * */
    private T data;

    public CommonResponseEntity(T data) {
        this.data = data;
        this.success = true;
//        if(data instanceof List) {
//            this.count = ((List<?>)data).size();
//        } else {
//            this.count = 1;
//        }
    }
}
