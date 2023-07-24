import request from "@/api/core/request";

export const updateInterviewQuestionResult = (data) => {
  const uri = `/api/interview-question-result`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.patch(uri, data, config);
};
