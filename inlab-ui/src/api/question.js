import request from "@/api/core/request";

export const getQuestion = (data) => {
  const uri = `/api/questions`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
    params: data,
  };
  return request.get(uri, config);
};

export const getFollowUpQuestion = (data) => {
  const uri = `/api/questions/${data.questionId}/question-versions`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
    params: data,
  };
  return request.get(uri, config);
};
