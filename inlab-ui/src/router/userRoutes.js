import SignInView from "@/views/users/SignInView.vue";
import SignUpView from "@/views/users/SignUpView.vue";
import UpdatePasswordView from "@/views/users/UpdatePasswordView.vue";

const userRoutes = [
  {
    path: "/",
    name: "sign-in",
    component: SignInView,
  },
  {
    path: "/sign-up",
    name: "sign-up",
    component: SignUpView,
  },
  {
    path: "/update-password",
    name: "update-password",
    component: UpdatePasswordView,
  },
];

export default userRoutes;
