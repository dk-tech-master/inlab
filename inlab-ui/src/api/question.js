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

export const getQuestionDetail = (questionId, username) => {
  const uri = `/api/questions/${questionId}`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
    params: { username },
  };
  return request.get(uri, config);
};

export const updateQuestion = (data, id) => {
  const uri = `/api/questions/${id}`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.post(uri, data, config);
};

export const createQuestion = (data) => {
  const uri = `/api/questions`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.post(uri, data, config);
};

export const getQuestionVersion = (data) => {
  const uri = `/api/questions/${data.questionId}/question-versions`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
    params: data,
  };
  return request.get(uri, config);
};

export const getFollowUpQuestion = (data) => {
  const uri = `/api/related-questions`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
    params: data,
  };
  return request.get(uri, config);
};

export const addFollowUpQuestion = (data) => {
  const uri = `/api/related-questions`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.post(uri, data, config);
};

export const deleteFollowUpQuestion = (relatedQuestionId) => {
  const uri = `/api/related-questions/${relatedQuestionId}`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
    params: { relatedQuestionId },
  };
  return request.delete(uri, config);
};
