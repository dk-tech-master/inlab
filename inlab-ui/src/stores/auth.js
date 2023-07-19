import { defineStore } from "pinia";
import { signIn } from "@/api/auth";
import { ref } from "vue";

export const authStore = defineStore("auth", () => {
  const questionTitle = ref(null);

  const setQuestionTitle = (question) => {
    questionTitle.value = question;
  };

  const getQuestionTitle = () => {
    return questionTitle.value;
  };

  const login = async (data) => {
    await signIn(data)
      .then(async (response) => {
        const accessToken = response.headers.get("Authorization");
        const refreshToken = response.headers.get("Refresh");
        const bodyData = await response.data;
        const { userId, nickname, role } = bodyData;

        sessionStorage.setItem("refreshToken", refreshToken);
        sessionStorage.setItem("accessToken", accessToken);
        sessionStorage.setItem("userId", userId);
        sessionStorage.setItem("nickname", nickname);
        sessionStorage.setItem("role", role);
        sessionStorage.setItem("email", data.username);
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
    sessionStorage.removeItem("role");
    location.reload();
    // location.reload();
  };

  return {
    questionTitle,
    setQuestionTitle,
    getQuestionTitle,
    login,
    logout,
  };
});
