<template>
  <section>
    <div
      class="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0"
    >
      <div class="w-full rounded-lg md:mt-0 sm:max-w-md xl:p-0">
        <h1
          class="mb-12 text-center text-2xl font-bold tracking-tight text-gray-800"
        >
          회원정보수정
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
                @click="createAuthenticationBtn"
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
              @click="updateBtn"
              type="submit"
              class="mt-15 btn btn-primary w-full"
            >
              회원정보수정
            </button>
          </div>
        </form>
      </div>
    </div>
  </section>
</template>
<script setup>
import { ref } from "vue";
import {
  checkVerificationCode,
  createVerificationCode,
  getUserInfo,
  updateUserInfo,
} from "@/api/auth";
import { useRouter } from "vue-router";

const email = ref("");
const verificationCode = ref("");
const password = ref("");
const confirmPassword = ref("");
const nickName = ref("");
//인증 번호 생성

let router = useRouter();
const init = async () => {
  const userInfo = await getUserInfo();
  email.value = userInfo.data.email;
  nickName.value = userInfo.data.nickname;
};

init();
const createAuthenticationBtn = async () => {
  let response = await createVerificationCode(email.value);
  if (response.status >= 200 && response.status < 400) {
    alert("인증 코드를 발송했습니다.");
  }
};
//인증 번호 확인
const checkVerificationCodeBtn = async () => {
  const data = {
    email: email.value,
    verificationCode: verificationCode.value,
  };
  console.log(data);
  let response = await checkVerificationCode(data);
  if (response.status === 200) {
    sessionStorage.setItem("email", response.headers.email);
    alert("인증번호 검증이 완료됐습니다.");
  }
};
const updateBtn = async (event) => {
  event.preventDefault();
  const data = {
    email: email.value,
    nickname: nickName.value,
    password: password.value,
  };
  let axiosResponse = await updateUserInfo(
    sessionStorage.getItem("email"),
    data,
  );

  if (axiosResponse.status >= 200 && axiosResponse.status < 300) {
    sessionStorage.removeItem("email");
    sessionStorage.removeItem("refreshToken");
    sessionStorage.removeItem("accessToken");
    sessionStorage.removeItem("role");
    sessionStorage.removeItem("userId");
    sessionStorage.removeItem("nickname");
    alert("회원 정보 수정이 완료되었습니다. 다시 로그인을 해주세요.");
    await router.push("/");
  } else {
    alert("회원가입에 실패했습니다.");
  }
};
</script>

<style scoped></style>
