import { createRouter, createWebHistory } from "vue-router";
import questionRoutes from "@/router/questionRoutes";
import categoryRoutes from "@/router/categoryRoutes";
import TestView from "@/views/TestView.vue";
import userRoutes from "@/router/userRoutes";
import interviewRoutes from "@/router/interviewRoutes";
import interviewerRoutes from "@/router/interviewerRoutes";
import AudioTestView from "@/views/AudioTestView.vue";
import userQuestionHistoryRoutes from "@/router/userQuestionHistory";
import NotFound from "@/views/common/NotFound.vue";

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
    {
      path: "/404",
      name: "notFound",
      component: NotFound,
    },
    {
      path: "/:pathMatch(.*)*",
      redirect: "/404",
    },
  ],
});

router.beforeEach((to, from, next) => {
  const accessToken = sessionStorage.getItem("accessToken");
  if (accessToken) {
    if (to.path === "/") {
      next("/question");
    } else {
      next();
    }
  } else {
    if (to.path === "/") {
      next();
    } else if (to.path === "/question") {
      next("/");
    } else if (to.path === "/sign-up") {
      next();
    } else {
      alert("로그인을 해주세요");
      next("/");
    }
  }
});

export default router;
