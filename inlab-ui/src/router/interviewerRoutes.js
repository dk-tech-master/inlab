import InterviewerManagement from "@/views/interviewer/InterviewerManagement.vue";

const interviewerRoutes = [
  {
    path: "/interviewer",
    name: "interviewerManagement",
    meta: {
      hide: true,
    },
    component: InterviewerManagement,
  },
];

export default interviewerRoutes;
