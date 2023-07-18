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
