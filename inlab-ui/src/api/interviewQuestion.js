import request from "@/api/core/request";

export const createInterview = (data) => {
  const uri = `/api/interview`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.post(uri, data, config);
};

export const getInterviewQuestion = (interviewId) => {
  const uri = `/api/interview/questions/${interviewId}`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.get(uri, config);
};

export const updateInterview = (data, interviewId) => {
  const uri = `/api/interview/${interviewId}`;
  console.log(interviewId);
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.put(uri, data, config);
};

export const getInterviewQuestionInfo = (interviewId) => {
  const uri = `/api/interview/questions/${interviewId}`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.get(uri, config);
};

export const createInterviewQuestion = (data) => {
  const uri = `/api/interview/questions`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.post(uri, data, config);
};

export const deleteInterviewQuestion = (interviewQuestionId) => {
  const uri = `/api/interview/questions/${interviewQuestionId}`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.delete(uri, config);
};
