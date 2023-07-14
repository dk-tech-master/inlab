package kr.inlab.www.service;

import java.util.List;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestGetQuestionTypeByPositionDto;
import kr.inlab.www.dto.request.RequestQuestionTypeDto;
import kr.inlab.www.dto.request.RequestCreateQuestionTypeDto;
import kr.inlab.www.dto.request.RequestUpdateQuestionTypeDto;
import kr.inlab.www.dto.response.ResponseGetQuestionTypeByPositionDto;
import kr.inlab.www.dto.response.ResponseQuestionTypeDto;

public interface QuestionTypeService {

    void createQuestionType(RequestCreateQuestionTypeDto requestDto);

    ResponseListDto<ResponseQuestionTypeDto> getQuestionType(RequestQuestionTypeDto requestDto);

    List<ResponseGetQuestionTypeByPositionDto> getQuestionTypeByPosition(RequestGetQuestionTypeByPositionDto requestDto);

    void updateQuestionType(Integer questionTypeId, RequestUpdateQuestionTypeDto requestDto);

    void deleteQuestionType(Integer questionTypeId);
}
