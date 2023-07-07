package kr.inlab.www.dto.common;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChecklistDto {

	private Long checklistId;
	private String content;

	@Builder
	public ChecklistDto(Long checklistId, String content) {
		this.checklistId = checklistId;
		this.content = content;
	}
}
