<template>
  <section>
    <div
      class="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0"
    >
      <div class="w-full rounded-lg md:mt-0 sm:max-w-md xl:p-0">
        <h1
          class="mb-12 text-center text-2xl font-bold tracking-tight text-gray-800"
        >
          회원가입
        </h1>
        <form class="space-y-4 md:space-y-6">
          <div>
            <label
              for="email"
              class="block mb-2 text-sm font-medium text-gray-900"
              >이메일</label
            >
            <div class="mb-4 flex justify-between">
              <input
                type="email"
                name="email"
                v-model="email"
                class="input input-bordered w-2/3 mr-2 border-gray-300 text-sm"
                placeholder="user@dktechin.com"
                required
              />
              <button
                @click="clickSendVerificationCodeBtn"
                type="button"
                class="btn btn-primary w-1/3"
              >
                인증번호발송
              </button>
            </div>
          </div>

          <div class="mb-4 flex justify-between">
            <input
              type="text"
              name="verification-code"
              v-model="verificationCode"
              class="input input-bordered w-2/3 mr-2 border-gray-300 text-sm"
              placeholder="이메일 인증번호를 입력하세요"
              required
            />
            <button
              @click="checkVerificationCodeBtn"
              type="button"
              class="btn btn-outline btn-primary w-1/3"
            >
              인증번호 확인
            </button>
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
              placeholder="••••••••"
              class="input input-bordered w-full mr-2 border-gray-300 text-sm"
              required
            />
          </div>
          <div>
            <label
              for="confirm-password"
              class="block mb-2 text-sm font-medium text-gray-900"
              >비밀번호 확인</label
            >
            <input
              type="confirm-password"
              name="confirm-password"
              v-model="confirmPassword"
              placeholder="••••••••"
              class="input input-bordered w-full mr-2 border-gray-300 text-sm"
              required
            />
          </div>
          <div>
            <label
              for="nickname"
              class="block mb-2 text-sm font-medium text-gray-900"
              >닉네임</label
            >
            <input
              type="nickname"
              name="nickname"
              v-model="nickName"
              placeholder="••••••••"
              class="input input-bordered w-full mr-2 border-gray-300 text-sm"
              required
            />
          </div>
          <div class="pt-5">
            <button
              @click="registerBtn"
              type="button"
              class="mt-15 btn btn-primary w-full"
            >
              회원가입
            </button>
            <p class="mt-3 text-center text-sm font-light text-gray-500">
              계정이 벌써 있으신가요?
              <a href="/" class="font-medium text-indigo-500 hover:underline"
                >로그인 바로가기</a
              >
            </p>
          </div>
        </form>
      </div>
    </div>
  </section>
</template>
<script setup>
import { useRouter } from "vue-router";
import { ref } from "vue";
import {
  checkVerificationCode,
  createVerificationCode,
  register,
} from "@/api/auth";

const router = useRouter();
const email = ref("");
const verificationCode = ref("");
const password = ref("");
const confirmPassword = ref("");
const nickName = ref("");

const clickSendVerificationCodeBtn = async () => {
  let response = await createVerificationCode(email.value);
  console.log(response);
  alert("인증 코드를 발송했습니다.");
};

const checkVerificationCodeBtn = async () => {
  const requestData = {
    email: email.value,
    verificationCode: verificationCode.value,
  };
  let response = await checkVerificationCode(requestData);
  sessionStorage.setItem("email", response.headers.email);
  alert("인증번호 검증이 완료됐습니다.");
};

const registerBtn = async () => {
  const requestData = {
    email: email.value,
    nickname: nickName.value,
    password: password.value,
  };
  let response = await register(sessionStorage.getItem("email"), requestData);

  if (response.status >= 200 && response.status < 300) {
    console.log(sessionStorage.getItem("email"));
    sessionStorage.removeItem(response.headers["clear-token"]);
    console.log(sessionStorage.getItem("email"));
    alert("회원가입이 완료됐습니다.");
    await router.push("/");
  }
};
</script>
