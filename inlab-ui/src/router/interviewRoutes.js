import StartInterview from "@/views/interview/StartInterview.vue";
import InterviewProcess from "@/views/interview/InterviewProcess.vue";
import InterviewManagement from "@/views/interview/InterviewManagement.vue";
import InterviewResultManagement from "@/views/interview/InterviewResultManagement.vue";
import InterviewDetail from "@/views/interview/InterviewDetail.vue";
import InterviewQuestionManagement from "@/views/interview/InterviewQuestionManagement.vue";
import InterviewResultDetail from "@/views/interview/InterviewResultDetail.vue";

const interviewRoutes = [
  {
    path: "/interview/start/:interviewId",
    name: "startInterview",
    meta: {
      hide: false,
    },
    component: StartInterview,
  },

  {
    path: "/interview/process",
    name: "interviewProcess",
    meta: {
      hide: false,
    },
    component: InterviewProcess,
  },
  {
    path: "/interview",
    name: "interviewManagement",
    meta: {
      hide: true,
    },
    component: InterviewManagement,
  },
  {
    path: "/interview/result",
    name: "interviewResultManagement",
    meta: {
      hide: true,
    },
    component: InterviewResultManagement,
  },
  {
    path: "/interview/detail/:interviewId",
    name: "interviewDetail",
    meta: {
      hide: true,
    },
    component: InterviewDetail,
  },
  {
    path: "/interview/question",
    name: "interviewQuestionManagement",
    meta: {
      hide: true,
    },
    component: InterviewQuestionManagement,
  },
  {
    path: "/interview/result/:interviewResultId",
    name: "interviewResultDetail",
    meta: {
      hide: true,
    },
    component: InterviewResultDetail,
  },
];

export default interviewRoutes;
