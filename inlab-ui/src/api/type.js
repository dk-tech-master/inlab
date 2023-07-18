import request from "@/api/core/request";

export const getTypes = (data) => {
  const uri = "/api/question-type";
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
    params: data,
  };
  return request.get(uri, config);
};

export const createTypes = (data) => {
  const uri = "/api/question-type";
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.post(uri, data, config);
};
