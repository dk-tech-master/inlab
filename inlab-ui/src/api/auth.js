import request from "@/api/core/request";

export const signIn = (data) => {
  const uri = `/login`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.post(uri, data, config);
};

export const createVerificationCode = (data) => {
  const uri = `/api/verification_code?email=${data}`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.post(uri, config);
};

export const checkVerificationCode = (data) => {
  const uri = `/api/verification_code/check`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.post(uri, data, config);
};

export const register = (token, data) => {
  const uri = `/api/users`;
  const config = {
    headers: {
      "Content-Type": "application/json",
      email: token,
    },
  };
  return request.post(uri, data, config);
};
