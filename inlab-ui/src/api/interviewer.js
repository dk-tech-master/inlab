import request from "@/api/core/request";

export const getInterviewers = (data) => {
  const uri = `/api/admin/users`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.post(uri, data, config);
};

export const updateApprove = (userId) => {
  const uri = `/api/admin/users/${userId}/role`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.put(uri, config);
};

export const getAccessVerification = (userId) => {
  const uri = `/api/position_level?userId=${userId}`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.get(uri, config);
};
export const updateAccessVerfication = (data) => {
  const uri = `/api/position_level`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.put(uri, data, config);
};

export const getCategory = () => {
  const uri = `/api/category`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.get(uri, config);
};
