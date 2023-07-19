import { createRouter, createWebHistory } from "vue-router";
import questionRoutes from "@/router/questionRoutes";
import categoryRoutes from "@/router/categoryRoutes";
import TestView from "@/views/TestView.vue";
import userRoutes from "@/router/userRoutes";
import interviewRoutes from "@/router/interviewRoutes";
import interviewerRoutes from "@/router/interviewerRoutes";
import AudioTestView from "@/views/AudioTestView.vue";
import userQuestionHistoryRoutes from "@/router/userQuestionHistory";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    ...userRoutes,
    ...interviewRoutes,
    ...interviewerRoutes,
    ...questionRoutes,
    ...categoryRoutes,
    ...userQuestionHistoryRoutes,
    {
      path: "/audioTest",
      name: "audioTest",
      component: AudioTestView,
    },
    {
      path: "/test",
      name: "test",
      component: TestView,
    },
  ],
});

router.beforeEach((to, from, next) => {
  const accessToken = sessionStorage.getItem("accessToken");
  if (accessToken === null || accessToken === "") {
    if (to.path === "/") {
      // 갈길이 로그인이면 로그인 가라~
      next();
    } else {
      alert("로그아웃 되었습니다.");
      next();
    }
  } else {
    // 주소창에 로그인으로 가려고 할 때
    if (to.path === "/") {
      next("/interview-management");
    } else {
      next();
    }
  }
});

export default router;
