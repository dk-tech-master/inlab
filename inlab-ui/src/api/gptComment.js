import request from "@/api/core/request";

export const getGptComment = (gptCommentId) => {
  const uri = `/api/gpt-comment/${gptCommentId}`;
  const config = {
    headers: {
      "Content-Type": "application/json",
    },
  };
  return request.get(uri, config);
};
