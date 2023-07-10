import JobManagement from "@/views/category/JobManagement.vue";
import TypeManagement from "@/views/category/TypeManagement.vue";

const categoryRoutes = [
  {
    path: "/question-categories/jobs",
    name: "jobs",
    component: JobManagement,
  },
  {
    path: "/question-categories/types",
    name: "types",
    component: TypeManagement,
  },
];
export default categoryRoutes;
