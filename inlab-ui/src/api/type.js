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

export const updateTypes = (data, id) => {
  const uri = `/api/question-type/${id}`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.put(uri, data, config);
};

export const deleteTypes = (id) => {
  const uri = `/api/question-type/${id}`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.delete(uri, config);
};