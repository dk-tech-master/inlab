import ncp from "@/api/core/ncp.js";

export const speechToText = (data) => {
  const uri = "/ncp/recognizer/url";
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return ncp.post(uri, data, config);
};
