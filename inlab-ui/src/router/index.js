import { createRouter, createWebHistory } from "vue-router";
import questionRoutes from "@/router/questionRoutes";
import categoryRoutes from "@/router/categoryRoutes";
import TestView from "@/views/TestView.vue";
import userRoutes from "@/router/userRoutes";
import interviewRoutes from "@/router/interviewRoutes";
import interviewerRoutes from "@/router/interviewerRoutes";
import AudioTestView from "@/views/AudioTestView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    ...userRoutes,
    ...interviewRoutes,
    ...interviewerRoutes,
    ...questionRoutes,
    ...categoryRoutes,
    {
      path: "/audioTest",
      name: "audioTest",
      component: AudioTestView,
    },
    {
      path: "/",
      name: "test",
      component: TestView,
    },
  ],
});

export default router;
