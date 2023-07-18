import request from "@/api/core/request";

export const getInterviewResultList = (data) => {
  const uri = `/api/interview-result`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
    params: data,
  };
  return request.get(uri, config);
};

export const getInterviewResult = (interviewResultId) => {
  const uri = `/api/interview-result/${interviewResultId}`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.get(uri, config);
};
