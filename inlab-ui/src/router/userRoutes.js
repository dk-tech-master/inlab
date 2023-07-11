import SignInView from "@/views/users/SignInView.vue";
import SignUpView from "@/views/users/SignUpView.vue";
import UpdatePasswordView from "@/views/users/UpdatePasswordView.vue";

const userRoutes = [
  {
    path: "/sign-in",
    name: "sign-in",
    component: SignInView,
  },
  {
    path: "/sign-up",
    name: "sign-up",
    component: SignUpView,
  },
  {
    path: "/updatePassword",
    name: "updatePassword",
    component: UpdatePasswordView,
  },
];

export default userRoutes;
