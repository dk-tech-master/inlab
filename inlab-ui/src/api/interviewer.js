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
