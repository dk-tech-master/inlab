import axios, { HttpStatusCode } from "axios";
import { ref } from "vue";
import { useRouter } from "vue-router";
// import { authStore } from "@/stores/auth";
// import { storeToRefs } from "pinia";

const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 30000,
});

const modal = ref(null);

request.interceptors.request.use(
  async (config) => {
    const accessToken = sessionStorage.getItem("accessToken");
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }
    console.log("성공했음");
    return config;
  },
  (error) => {
    console.log(error);
    return Promise.reject(error);
  },
);

request.interceptors.response.use(
  async (response) => {
    const statuses = [
      HttpStatusCode.Ok,
      HttpStatusCode.Created,
      HttpStatusCode.NoContent,
    ];
    const router = useRouter();
    if (statuses.includes(response.status)) {
      console.log("axios request success");
    }
    if (response.headers["access-token-expired"]) {
      const refreshToken = sessionStorage.getItem("refreshToken");
      // 새로운 access 토큰 발급 및 헤더에 포함시키는 로직 구현
      const config = response.config;
      config.headers["Refresh"] = refreshToken;
      // 이전 요청 재실행
      const newResponse = await axios.request(config);
      const newAccessToken = newResponse.headers["authorization"];
      if (newAccessToken) {
        // 원하는 처리를 수행 (예: 저장)
        sessionStorage.setItem("accessToken", newAccessToken);
      }
      return newResponse;
    } else if (response.headers["access-token-invalid"]) {
      sessionStorage.removeItem("refreshToken");
      sessionStorage.removeItem("accessToken");
      await router.push("/sign-in");
    }
    return response;
  },
  async (error) => {
    console.log(error);
  },
);

export default request;
