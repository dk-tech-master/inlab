package kr.inlab.www.service;

import kr.inlab.www.entity.Checklist;
import kr.inlab.www.entity.QuestionVersion;

import java.util.List;

public interface ChecklistService {

    List<Checklist> getChecklists(QuestionVersion questionVersion);
}
