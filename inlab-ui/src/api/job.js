import request from "@/api/core/request";

export const getJobs = (data) => {
  const uri = "/api/position";
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
    params: data,
  };
  return request.get(uri, config);
};

export const createJobs = (data) => {
  const uri = `/api/position`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.post(uri, data, config);
};

export const deleteJobs = (id) => {
  const uri = "/api/position/" + id;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.delete(uri, config);
};

export const updateJobs = (data, id) => {
  const uri = "/api/position/" + id;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.put(uri, data, config);
};
