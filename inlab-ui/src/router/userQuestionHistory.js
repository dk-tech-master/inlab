import UserQuestionHistoryManagement from "@/views/userQuestionHistory/UserQuestionHistoryManagement.vue";

const userQuestionHistoryRoutes = [
  {
    path: "/user-question-history",
    name: "userQuestionHistoryList",
    meta: {
      hide: true,
    },
    component: UserQuestionHistoryManagement,
  },
];

export default userQuestionHistoryRoutes;
