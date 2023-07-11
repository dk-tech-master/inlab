import axios, { HttpStatusCode } from "axios";
import { storeToRefs } from "pinia";
import { authStore } from "@/stores/auth";
// import { authStore } from "@/stores/auth";
// import { storeToRefs } from "pinia";

const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 30000,
});

request.interceptors.request.use(
  async (config) => {
    const store = authStore();
    const accessToken = store.accessToken;
    if (accessToken) {
      config.headers.Authorization = `${accessToken}`;
    }
    return config;
  },
  (error) => {
    console.log(error);
    return Promise.reject(error);
  },
);

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
    if (response.headers["Access-Token-Expired"]) {
    }
    return response;
  },
  async (error) => {},
);

export default request;
