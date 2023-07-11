import { defineStore } from "pinia";
import { signIn } from "@/api/auth";
import { ref } from "vue";

export const authStore = defineStore("auth", () => {
  const accessToken = ref("");
  const login = async (data) => {
    const response = await signIn(data);
    accessToken.value = response.headers.get("Authorization");
    const refreshToken = response.headers.get("Refresh");
    sessionStorage.setItem("refresh", refreshToken);
  };

  const logout = () => {
    // sessionStorage.removeItem("refreshToken");
    // location.reload();
  };

  return { accessToken, login, logout };
});
