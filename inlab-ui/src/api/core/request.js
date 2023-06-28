import axios, { HttpStatusCode } from "axios";

const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 30000,
});

request.interceptors.response.use(
  (response) => {
    const statuses = [
      HttpStatusCode.Ok,
      HttpStatusCode.Created,
      HttpStatusCode.NoContent,
    ];
    if (statuses.includes(response.status)) {
      console.log("axios request success");
    }
    return response;
  },
  async (error) => {
    console.log("axios request fail");
    alert(error.response.data.message);
    return Promise.reject(error);
  },
);

export default request;
