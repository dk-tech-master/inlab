import SignInView from "@/views/users/SignInView.vue";
import SignUpView from "@/views/users/SignUpView.vue";
import UpdateUserInfoView from "@/views/users/UpdateUserInfoView.vue";

const userRoutes = [
  {
    path: "/",
    name: "sign-in",
    meta: {
      hide: false,
    },
    component: SignInView,
  },
  {
    path: "/sign-up",
    name: "sign-up",
    meta: {
      hide: false,
    },
    component: SignUpView,
  },
  {
    path: "/update-userinfo",
    name: "update-userinfo",
    meta: {
      hide: false,
    },
    component: UpdateUserInfoView,
  },
];

export default userRoutes;
