<template>
  <nav class="px-20 navbar border-b shadow-sm flex justify-end">
    <div v-if="isAccessTokenPresent" class="flex items-center gap-x-4 py-2">
      <div class="flex items-center gap-x-2">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 20 20"
          fill="currentColor"
          class="w-6 h-6"
        >
          <path
            fill-rule="evenodd"
            d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-5.5-2.5a2.5 2.5 0 11-5 0 2.5 2.5 0 015 0zM10 12a5.99 5.99 0 00-4.793 2.39A6.483 6.483 0 0010 16.5a6.483 6.483 0 004.793-2.11A5.99 5.99 0 0010 12z"
            clip-rule="evenodd"
          />
        </svg>
        <span>{{ nickname }} 님</span>
      </div>

      <button class="btn btn-sm" @click="clickUpdateUserInfoBtn">
        회원정보 수정
      </button>

      <button
        type="button"
        class="btn btn-primary btn-sm btn-outline"
        @click="logoutBtn"
      >
        로그아웃
      </button>
    </div>
    <div v-else>
      <a href="/">
        <button
          type="button"
          class="flex flex-col items-center px-7 py-5 btn btn-primary btn-sm btn btn-outline text-sm"
        >
          로그인
        </button>
      </a>
    </div>
  </nav>
</template>

<script setup>
import { computed, ref } from "vue";
import { authStore } from "@/stores/auth";
import { useRouter } from "vue-router";

const router = useRouter();
// const router = useRouter();
const store = authStore();

const nickname = ref(sessionStorage.getItem("nickname"));
const isAccessTokenPresent = computed(() =>
  sessionStorage.getItem("accessToken"),
);

const logoutBtn = async () => {
  await store.logout();
  // await router.push("/");
};

const loginBtn = async () => {};

const clickUpdateUserInfoBtn = () => {
  router.push("/update-userinfo");
};
</script>
