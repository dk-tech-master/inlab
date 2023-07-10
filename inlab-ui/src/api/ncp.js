import request from "@/api/core/request.js";

export const speechToText = (data) => {
  const uri = "/ncp/recognizer/url";
  const config = {
    headers: {
      "X-CLOVASPEECH-API-KEY": "7eabeb6d6c59495580eba2f5c3591c22",
      "Content-Type": "application/json",
    },
  };
  return request.post(uri, data, config);
};
