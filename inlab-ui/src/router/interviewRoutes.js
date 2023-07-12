import StartInterview from "@/views/interview/StartInterview.vue";
import InterviewProcess from "@/views/interview/InterviewProcess.vue";
import InterviewManagement from "@/views/interview/InterviewManagement.vue";
import InterviewResultManagement from "@/views/interview/InterviewResultManagement.vue";
import InterviewDetail from "@/views/interview/InterviewDetail.vue";
import InterviewQuestionManagement from "@/views/interview/InterviewQuestionManagement.vue";
import InterviewResultDetail from "@/views/interview/InterviewResultDetail.vue";

const interviewRoutes = [
  {
    path: "/start-interview",
    name: "startInterview",
    component: StartInterview,
  },

  {
    path: "/interview-process",
    name: "interviewProcess",
    component: InterviewProcess,
  },
  {
    path: "/interview-management",
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
];

export default interviewRoutes;
