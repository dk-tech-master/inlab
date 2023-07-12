import { defineStore } from "pinia";
import { signIn } from "@/api/auth";

export const authStore = defineStore("auth", () => {
  const login = async (data) => {
    const response = await signIn(data);
    const accessToken = response.headers.get("Authorization");
    const refreshToken = response.headers.get("Refresh");
    sessionStorage.setItem("refreshToken", refreshToken);
    sessionStorage.setItem("accessToken", accessToken);
  };

  const logout = () => {
    sessionStorage.removeItem("refreshToken");
    sessionStorage.removeItem("accessToken");
    location.reload();
    // location.reload();
  };

  return { login, logout };
});
