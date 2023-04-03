package com.yaloostore.shop.docs;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;


/**
 * 명세 문서가 생성되기 직전에 일괄적으로 해당 명세에 대한 일종의 선행 처리과정을 정의한 클래스입니다.
 * (선행 처리작업을 여기서 설정한 대로 진행 가능)
 * */
public class RestApiDocumentation {


    /**
     * 요청 API에 대한 기본 도메인, 포트를 정의해준다.
     * 처리된 결과를 PrettyPrint()를 통해서 깔끔하게 보여질 수 있게 했다.
     * */
    public static OperationRequestPreprocessor getDocumentRequest(){
        return preprocessRequest(modifyUris().scheme("https")
                .host("localhost")
                .port(8081)
                .removePort(), prettyPrint());
    }

    /**
     * 처리된 결과에 동일하게 PrettyPrint()를 적용해서 결과를 깔금하게 보여줄 수 있게 했다.
     * */
    public static OperationResponsePreprocessor getDocumentsResponse(){
        return preprocessResponse(prettyPrint());
    }

}
