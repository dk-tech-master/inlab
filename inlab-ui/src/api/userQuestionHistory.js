import request from "@/api/core/request";

export const getUserQuestionHistoryList = (data) => {
  const uri = "/api/user-question-history";
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
    params: data,
  };
  return request.get(uri, config);
};
