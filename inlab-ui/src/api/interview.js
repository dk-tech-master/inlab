import request from "@/api/core/request";

export const getInterviewQuestionList = (interviewId) => {
  const uri = `/api/interview/start/${interviewId}`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.get(uri, config);
};