import JobManagement from "@/views/category/JobManagement.vue";
import TypeManagement from "@/views/category/TypeManagement.vue";

const categoryRoutes = [
  {
    path: "/category/job",
    name: "jobs",
    meta: {
      hide: true,
    },
    component: JobManagement,
  },
  {
    path: "/category/type",
    meta: {
      hide: true,
    },
    name: "types",
    component: TypeManagement,
  },
];
export default categoryRoutes;
