package kr.inlab.www.service;

import kr.inlab.www.common.exception.*;
import kr.inlab.www.common.type.RoleType;
import kr.inlab.www.common.util.PagingUtil;
import kr.inlab.www.dto.common.PositionAndLevelList;
import kr.inlab.www.dto.common.PositionDto;
import kr.inlab.www.dto.common.ResponseListDto;
import kr.inlab.www.dto.request.RequestGetPositionDto;
import kr.inlab.www.dto.request.RequestPositionNameDto;
import kr.inlab.www.dto.response.ResponsePositionDto;
import kr.inlab.www.entity.Position;
import kr.inlab.www.entity.PositionLevel;
import kr.inlab.www.entity.Role;
import kr.inlab.www.entity.User;
import kr.inlab.www.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final PositionLevelRepository positionLevelRepository;
    private final QuestionTypeRepository questionTypeRepository;
    private final PositionLevelService positionLevelService;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void createPosition(RequestPositionNameDto requestDto) throws RoleNotFoundException {
        if (positionRepository.existsByPositionName(requestDto.getPositionName()))
            throw new PositionAlreadyExistsException();

        Position position = Position.builder()
                .positionName(requestDto.getPositionName())
                .build();
        positionRepository.save(position);
        Role role = roleRepository.findByRoleType(RoleType.ROLE_ADMIN).orElseThrow(RoleNotFoundException::new);
        List<User> adminUserList = userRepository.findAllByRoles(role);
        positionLevelService.createAdminPositionLevel(adminUserList, position);
    }

    @Override
    public ResponseListDto<ResponsePositionDto> getPosition(RequestGetPositionDto requestDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), Sort.Direction.DESC, "positionId");
        Page<ResponsePositionDto> positionList = positionRepository.getPositionsList(requestDto.getPositionName(), pageable);
        PagingUtil pagingUtil = new PagingUtil(positionList.getTotalElements(), positionList.getTotalPages(), positionList.getNumber(), positionList.getSize());

        return new ResponseListDto<ResponsePositionDto>(positionList.getContent(), pagingUtil);
    }

    @Override
    @Transactional
    public void deletePosition(Integer positionId) {
        Position position = positionRepository.findById(positionId)
                .orElseThrow(PositionNotFoundException::new);

        if (questionRepository.existsByPosition_PositionId(positionId) ||
                questionTypeRepository.existsByPosition_PositionId(positionId))
            throw new PositionDeleteNotAllowedException();

        positionLevelRepository.deleteAllByPosition(position);
        positionRepository.delete(position);
    }

    @Override
    @Transactional
    public void updatePosition(Integer positionId, RequestPositionNameDto requestDto) {
        Position position = positionRepository.findById(positionId)
                .orElseThrow(PositionNotFoundException::new);

        if (questionRepository.existsByPosition_PositionId(positionId))
            throw new PositionUpdateNotAllowedException();

        if (positionRepository.existsByPositionName(requestDto.getPositionName()))
            throw new PositionAlreadyExistsException();

        position.updateName(requestDto.getPositionName());
    }

    @Override
    public List<PositionAndLevelList> getPositionOnCategory() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        List<PositionLevel> positionLevels = positionLevelRepository.findByUser(user);

        Map<Integer, List<PositionLevel>> groupedPositionLevels = positionLevels.stream()
                .collect(Collectors.groupingBy(positionLevel -> positionLevel.getPosition().getPositionId()));

        return groupedPositionLevels.entrySet().stream()
                .map(entry -> {
                    List<PositionAndLevelList.LevelDto> levelListDto = entry.getValue().stream()
                            .map(positionLevel -> PositionAndLevelList.LevelDto.builder()
                                    .levelId(positionLevel.getLevelId())
                                    .levelName(positionLevel.getQuestionLevel().getQuestionLevelName()) // This line assumes that getQuestionLevelName() is available in QuestionLevel class.
                                    .build())
                            .collect(Collectors.toList());

                    return PositionAndLevelList.builder()
                            .positionId(entry.getKey())
                            .positionName(entry.getValue().get(0).getPosition().getPositionName()) // This line assumes that getPositionName() is available in Position class.
                            .levelListDto(levelListDto)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PositionDto> getAllPosition() {
        return positionRepository.findAll().stream().map(Position::toDto).collect(Collectors.toList());
    }
}
