import request from "@/api/core/request";

export const getpositionOption = () => {
  const uri = "/api/position/category";
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.get(uri, config);
};

export const getTypeOption = (data) => {
  const uri = `/api/question-type/category`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
    params: data,
  };
  return request.get(uri, config);
};
