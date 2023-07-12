import SignInView from "@/views/users/SignInView.vue";
import SignUpView from "@/views/users/SignUpView.vue";
import UpdateUserInfoView from "@/views/users/UpdateUserInfoView.vue";

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
    path: "/update-userinfo",
    name: "update-userinfo",
    component: UpdateUserInfoView,
  },
];

export default userRoutes;
