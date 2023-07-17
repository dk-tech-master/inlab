<template>
  <section>
    <div
      class="flex flex-col items-center justify-center w-full px-6 py-8 md:h-screen lg:py-0"
    >
      <div class="w-1/3 flex items-center">
        <a href="#" class="flex w-1/3 items-center mx-auto mb-10">
          <img class="w-full" src="../../images/logo-color.svg" alt="logo" />
        </a>
      </div>
      <div class="w-full bg-white rounded-lg shadow md:mt-0 sm:max-w-md xl:p-0">
        <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
          <form class="" action="#">
            <div class="mb-4">
              <label
                for="email"
                class="block mb-2 text-sm font-medium text-gray-900"
                >이메일</label
              >
              <input
                type="email"
                name="email"
                v-model="email"
                class="input input-bordered border-gray-300 w-full text-sm"
                placeholder="user@dktechin.com"
                required
              />
            </div>
            <div>
              <label
                for="password"
                class="block mb-2 text-sm font-medium text-gray-900"
                >비밀번호</label
              >
              <input
                type="password"
                name="password"
                v-model="password"
                placeholder="비밀번호 입력"
                class="input input-bordered border-gray-300 w-full text-sm"
                required
              />
            </div>
            <button
              type="button"
              class="btn btn-primary w-full mt-10"
              @click="clickSignInBtn"
            >
              로그인
            </button>
            <button
              type="button"
              class="btn btn-outline btn-primary w-full mt-2"
              @click="clickSignUpBtn"
            >
              회원가입
            </button>
          </form>
        </div>
      </div>
    </div>
  </section>
  <VModal ref="modal" class="text-center m">
    <template v-slot:header>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 24 24"
        stroke-width="1.5"
        stroke="currentColor"
        class="w-12 h-12 text-primary mx-auto mb-3"
      >
        <path
          stroke-linecap="round"
          stroke-linejoin="round"
          d="M15.182 16.318A4.486 4.486 0 0012.016 15a4.486 4.486 0 00-3.198 1.318M21 12a9 9 0 11-18 0 9 9 0 0118 0zM9.75 9.75c0 .414-.168.75-.375.75S9 10.164 9 9.75 9.168 9 9.375 9s.375.336.375.75zm-.375 0h.008v.015h-.008V9.75zm5.625 0c0 .414-.168.75-.375.75s-.375-.336-.375-.75.168-.75.375-.75.375.336.375.75zm-.375 0h.008v.015h-.008V9.75z"
        />
      </svg>
      <h3 class="mb-7 text-xl font-semibold tracking-tighter text-gray-800">
        로그인 실패
      </h3>
    </template>
    <template v-slot:body>
      <p class="mb-8 text-base leading-relaxed font-medium text-gray-800">
        이메일(로그인 전용 아이디) 또는 <br />
        비밀번호 입력 허용 횟수를 5회 초과하였습니다.<br />
        해당 계정은 일시적으로 로그인이 차단됩니다.
      </p>
      <div class="mb-8">
        <span
          class="text-base leading-relaxed font-medium text-gray-600 text-primary"
        >
          남은 시간 :
        </span>
        <span class="countdown font-mono text-xl">
          <span style="--value: 10"></span>분
          <span style="--value: 24"></span>초
        </span>
      </div>
    </template>
    <template v-slot:footer="footerProps">
      <div class="modal-action">
        <button
          class="btn btn-primary w-full mt-5"
          @click="footerProps.toggleModal"
        >
          확인
        </button>
      </div>
    </template>
  </VModal>
</template>

<script setup>
import VModal from "@/components/teleport/VModal.vue";
import { ref } from "vue";
import { authStore } from "@/stores/auth";
import { useRouter } from "vue-router";

const store = authStore();
const router = useRouter();
const modal = ref(null);
const email = ref();
const password = ref();

const clickSignInBtn = async () => {
  const requestData = {
    username: email.value,
    password: password.value,
  };
  await store
    .login(requestData)
    .then(async () => {
      await router.push("/interviewer");
    })
    .catch(async (error) => {
      if (error.response.headers["password-change-required"]) {
        alert("비밀번호 변경 기간이 3개월 이상이 넘었습니다.");
      } else if (error.response.headers["login-fail-block"]) {
        alert("로그인 시도 횟수가 5회 이상이 되어 30분간 로그인이 제한됩니다.");
        modal.value.toggleModal();
      } else if (error.response.headers["login-fail-delete"]) {
        alert("탈퇴한 회원입니다.");
      } else if (error.response.headers["login-fail"]) {
        alert("비밀번호 또는 이메일이 일치 하지 않습니다.");
      }
      await router.push("/");
    });
};

const clickSignUpBtn = () => {
  router.push("/sign-up");
};
</script>
