import QuestionManagement from "@/views/question/QuestionManagement.vue";
import QuestionVersionManagement from "@/views/question/QuestionVersionManagement.vue";
import FollowUpQuestionManagement from "@/views/interview/FollowUpQuestionManagement.vue";

const questionRoutes = [
  {
    path: "/questions",
    name: "/questions",
    component: QuestionManagement,
  },
  {
    path: "/questions/versions",
    name: "/versions",
    component: QuestionVersionManagement,
  },
  {
    path: "/questions/follow-up",
    name: "/follow-up",
    component: FollowUpQuestionManagement,
  },
];

export default questionRoutes;
