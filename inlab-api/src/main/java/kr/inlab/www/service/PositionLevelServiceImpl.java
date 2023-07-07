package kr.inlab.www.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import kr.inlab.www.common.exception.PositionNotFoundException;
import kr.inlab.www.common.exception.QuestionLevelNotFoundException;
import kr.inlab.www.common.exception.UserNotFoundException;
import kr.inlab.www.dto.common.PositionAndLevelInfo;
import kr.inlab.www.dto.response.ResponseGetPositionLevelDto;
import kr.inlab.www.dto.common.PositionLevelInfoGetter;
import kr.inlab.www.dto.request.RequestUpdatePositionLevelDto;
import kr.inlab.www.entity.PositionLevel;
import kr.inlab.www.repository.PositionLevelRepository;
import kr.inlab.www.repository.PositionRepository;
import kr.inlab.www.repository.QuestionLevelRepository;
import kr.inlab.www.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PositionLevelServiceImpl implements PositionLevelService {

    private final PositionLevelRepository positionLevelRepository;
    private final PositionRepository positionRepository;
    private final QuestionLevelRepository questionLevelRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseGetPositionLevelDto getPositionLevel(Long userId) {

        List<PositionAndLevelInfo> positionAndLevelInfos = positionLevelRepository
            .findByUser(userRepository.findById(userId).orElseThrow(UserNotFoundException::new)).stream()
            .map(positionLevel ->
                PositionAndLevelInfo.builder()
                    .positionId(positionLevel.getPositionId())
                    .positionName(positionRepository.findById(positionLevel.getPositionId())
                        .orElseThrow(PositionNotFoundException::new).getPositionName())
                    .levelId(positionLevel.getLevelId())
                    .levelName(questionLevelRepository.findById(positionLevel.getLevelId())
                        .orElseThrow(QuestionLevelNotFoundException::new).getQuestionLevelName())
                    .build()).collect(Collectors.toList());

        return ResponseGetPositionLevelDto.builder()
                .positionIdAndLevelIds(positionAndLevelInfos).build();
    }

    @Override
    @Transactional
    public void updatePositionLevel(RequestUpdatePositionLevelDto requestDto) {
        List<PositionLevel> positionLevels = positionLevelRepository.findByUser(userRepository.findById(requestDto.getUserId()).orElseThrow(UserNotFoundException::new));

        //  미리 부여된 권한들 중 부여할 권한에 없는 PositionQuestionId 를 찾아서 삭제한다.
        // todo N+1
        positionLevelRepository.deleteAllById(positionLevels
            .stream()
            .filter(positionLevel -> anyPositionLevelInfoMatchInList(positionLevel.getPosition().getPositionId(),
                positionLevel.getQuestionLevel().getQuestionLevelId(), requestDto.getPositionAndLevelInfos()))
            .map(PositionLevel::getPositionLevelId)
            .collect(Collectors.toList()));
        // 미리 부여된 권한들 중 없는 권한만 추가한다.
        positionLevelRepository.saveAll(requestDto.getPositionAndLevelInfos()
            .stream()
            .filter(positionLevelInfo -> anyPositionLevelInfoMatchInList(positionLevelInfo.getPositionId(),
                positionLevelInfo.getLevelId(), positionLevels))
            .map(positionAndLevelInfo -> PositionLevel.builder()
                .position(positionRepository.findById(positionAndLevelInfo.getPositionId()).orElseThrow(
                    PositionNotFoundException::new))
                .questionLevel(questionLevelRepository.findById(positionAndLevelInfo.getLevelId()).orElseThrow(
                    QuestionLevelNotFoundException::new))
                .user(userRepository.findById(requestDto.getUserId()).orElseThrow(UserNotFoundException::new))
                .build())
            .collect(Collectors.toList())
        );
    }

    // 리스트에 값이 있으면 true 반환
    private boolean anyPositionLevelInfoMatchInList(Integer positionId, Integer questionLevelId,
        List<? extends PositionLevelInfoGetter> positionLevelInfo) {
        return positionLevelInfo.stream().noneMatch(ele ->
            Objects.equals(ele.getLevelId(), questionLevelId) && Objects.equals(ele.getPositionId(), positionId));
    }

}
