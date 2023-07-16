import QuestionManagement from "@/views/question/QuestionManagement.vue";
import QuestionVersionManagement from "@/views/question/QuestionVersionManagement.vue";
import FollowUpQuestionManagement from "@/views/interview/FollowUpQuestionManagement.vue";

const questionRoutes = [
  {
    path: "/question",
    name: "/questions",
    meta: {
      hide: true,
    },
    component: QuestionManagement,
  },
  {
    path: "/question/version",
    name: "/versions",
    meta: {
      hide: true,
    },
    component: QuestionVersionManagement,
  },
  {
    path: "/question/follow-up",
    name: "/follow-up",
    meta: {
      hide: true,
    },
    component: FollowUpQuestionManagement,
  },
];

export default questionRoutes;
