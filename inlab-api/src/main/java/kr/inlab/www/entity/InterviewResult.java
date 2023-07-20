package kr.inlab.www.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import kr.inlab.www.dto.response.ResponseInterviewResultListDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class InterviewResult {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long interviewResultId;

	private String intervieweeName;

	@CreatedDate
	private LocalDateTime createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "interview_id")
	private Interview interview;

	@Builder
	public InterviewResult(String intervieweeName, Interview interview) {
		this.intervieweeName = intervieweeName;
		this.interview = interview;
	}

	public ResponseInterviewResultListDto toResponseInterviewResultListDto() {
		return ResponseInterviewResultListDto.builder()
			.interviewResultId(this.interviewResultId)
			.interviewTitle(this.interview.getTitle())
			.intervieweeName(this.intervieweeName)
			.nickname(this.interview.getUser().getNickname())
			.createdAt(createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
			.build();
	}
}
