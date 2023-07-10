import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/AudioTestView.vue";
import StartInterview from "@/views/interview/StartInterview.vue";
import SignInView from "@/views/users/SignInView.vue";
import SignUpView from "@/views/users/SignUpView.vue";
import UpdatePasswordView from "@/views/users/UpdatePasswordView.vue";
import InterviewProcess from "@/views/interview/InterviewProcess.vue";
import InterviewManagement from "@/views/interview/InterviewManagement.vue";
import InterviewDetail from "@/views/interview/InterviewDetail.vue";
import InterviewQuestionManagement from "@/views/interview/InterviewQuestionManagement.vue";
import InterviewResultManagement from "@/views/interview/InterviewResultManagement.vue";
import InterviewResultDetail from "@/views/interview/InterviewResultDetail.vue";
import AudioTestView from "@/views/AudioTestView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/audioTest",
      name: "audioTest",
      component: AudioTestView,
    },
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/signIn",
      name: "signIn",
      component: SignInView,
    },
    {
      path: "/signUp",
      name: "signUp",
      component: SignUpView,
    },
    {
      path: "/startInterview",
      name: "startInterview",
      component: StartInterview,
    },
    {
      path: "/updatePassword",
      name: "updatePassword",
      component: UpdatePasswordView,
    },
    {
      path: "/interviewProcess",
      name: "interviewProcess",
      component: InterviewProcess,
    },
    {
      path: "/interviewManagement",
      name: "interviewManagement",
      component: InterviewManagement,
    },
    {
      path: "/interviewResultManagement",
      name: "interviewResultManagement",
      component: InterviewResultManagement,
    },
    {
      path: "/interviewDetail",
      name: "interviewDetail",
      component: InterviewDetail,
    },
    {
      path: "/interviewQuestionManagement",
      name: "interviewQuestionManagement",
      component: InterviewQuestionManagement,
    },
    {
      path: "/interviewResultDetail",
      name: "interviewResultDetail",
      component: InterviewResultDetail,
    },
  ],
});

export default router;
