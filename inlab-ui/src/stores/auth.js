import { defineStore } from "pinia";
import { signIn } from "@/api/auth";

export const authStore = defineStore("auth", () => {
  const login = async (data) => {
    await signIn(data)
      .then((response) => {
        const accessToken = response.headers.get("Authorization");
        const refreshToken = response.headers.get("Refresh");
        const userId = response.headers.get("user-id");
        const nickname = response.headers.get("nickname");
        console.log(response);

        sessionStorage.setItem("refreshToken", refreshToken);
        sessionStorage.setItem("accessToken", accessToken);
        sessionStorage.setItem("userId", userId);
        sessionStorage.setItem("nickname", nickname);
        return response;
      })
      .catch((error) => {
        return Promise.reject(error);
      });
  };

  const logout = () => {
    sessionStorage.removeItem("refreshToken");
    sessionStorage.removeItem("accessToken");
    sessionStorage.removeItem("userId");
    sessionStorage.removeItem("nickname");
    location.reload();
    // location.reload();
  };

  return { login, logout };
});
