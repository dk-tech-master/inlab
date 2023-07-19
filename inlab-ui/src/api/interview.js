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

export const getInterviewList = (data, userId) => {
  console.log(data);
  const uri = `/api/interview/${userId}`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
    params: data,
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
