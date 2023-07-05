package kr.inlab.www.service;

import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestQuestionTypeDto;
import kr.inlab.www.dto.request.RequestQuestionTypeNameDto;
import kr.inlab.www.dto.response.ResponseQuestionTypeDto;

public interface QuestionTypeService {

    void createQuestionType(RequestQuestionTypeNameDto requestDto);

    ResponseListDto<ResponseQuestionTypeDto> getQuestionType(RequestQuestionTypeDto requestDto);

    void updateQuestionType(Integer questionTypeId, RequestQuestionTypeNameDto reqeustDto);

    void deleteQuestionType(Integer questionTypeId);
}
