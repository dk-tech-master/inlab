import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import UserLogin from "../views/users/UserLogin.vue";
import SignUp from "../views/users/SignUp.vue";
import UpdatePassword from "../views/users/UpdatePassword.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/login",
      name: "login",
      component: UserLogin,
    },
    {
      path: "/signUp",
      name: "signUp",
      component: SignUp,
    },
    {
      path: "/updatePassword",
      name: "updatePassword",
      component: UpdatePassword,
    },
  ],
});

export default router;
