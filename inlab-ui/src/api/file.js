import request from "@/api/core/request.js";

export const uploadAudioFile = (data) => {
  const uri = "/api/file-upload";
  const config = {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  };
  return request.post(uri, data, config);
};
