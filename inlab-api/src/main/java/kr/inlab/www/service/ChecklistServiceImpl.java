package kr.inlab.www.service;

import kr.inlab.www.entity.Checklist;
import kr.inlab.www.entity.QuestionVersion;
import kr.inlab.www.repository.ChecklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChecklistServiceImpl implements ChecklistService {

    private final ChecklistRepository checklistRepository;

    @Override
    public List<Checklist> getChecklists(QuestionVersion questionVersion) {
        List<Checklist> checklists = checklistRepository.findAllByQuestionVersion(questionVersion);
        return checklists;
    }

    @Override
    public void saveChecklists(List<String> checklists, QuestionVersion questionVersion) {
        List<Checklist> checklistEntities = checklists.stream()
            .map(checklist -> Checklist.builder()
                .content(checklist)
                .questionVersion(questionVersion)
                .build())
            .collect(Collectors.toList());

        checklistRepository.saveAll(checklistEntities);
    }
}
