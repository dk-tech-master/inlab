import { defineStore } from "pinia";
import { signIn } from "@/api/auth";
import { ref } from "vue";

export const authStore = defineStore("auth", () => {
  let email = ref("");
  const login = async (data) => {
    await signIn(data)
      .then((response) => {
        email.value = data.username;
        console.log(data.username); //아이디 찍힘
        console.log(email.value); //아이디 찍힘
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

  const getEmail = () => {
    return email.value;
  };

  return { email, getEmail, login, logout };
});
