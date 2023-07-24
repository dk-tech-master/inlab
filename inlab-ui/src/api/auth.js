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
  if (sessionStorage.getItem("accessToken")) {
    return request.put(uri, config);
  } else {
    return request.post(uri, config);
  }
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

export const checkEmailDuplicated = (email) => {
  const uri = `/api/users/check-email-duplicated`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
    params: {
      email: email,
    },
  };
  return request.get(uri, config);
};

export const checkNicknameDuplicated = (nickname) => {
  const uri = `/api/users/check-nickname-duplicated`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
    params: {
      nickname: nickname,
    },
  };
  return request.get(uri, config);
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

export const getUserInfo = () => {
  const userId = sessionStorage.getItem("userId");
  const uri = `/api/users/${userId}`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.get(uri, config);
};

export const updateUserInfo = (token, data) => {
  const userId = sessionStorage.getItem("userId");
  const uri = `/api/users/${userId}`;
  const config = {
    headers: {
      "Content-Type": "application/json",
      email: token,
    },
  };
  return request.put(uri, data, config);
};

export const deleteUser = (userId) => {
  const uri = `/api/admin/users/${userId}`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  console.log(uri);
  return request.delete(uri, config);
};
