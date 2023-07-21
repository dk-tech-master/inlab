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
                :class="`input input-bordered w-2/3 mr-2 text-sm ${
                  isEmailInputStarted
                    ? !isValidEmail && !isDuplicatedEmail
                      ? 'border-success'
                      : 'border-error'
                    : 'border-gray-300'
                }`"
                placeholder="user@dktechin.com"
                required
              />
              <button
                @click="clickSendVerificationCodeBtn"
                type="button"
                :disabled="isValidEmail || isDuplicatedEmail"
                class="btn btn-primary w-1/3"
              >
                인증번호발송
              </button>
            </div>
            <p v-if="!isEmailInputStarted"></p>
            <p v-else-if="isValidEmail" class="text-error">
              유효하지 않은 이메일 입니다.
            </p>
            <p v-else-if="isDuplicatedEmail" class="text-error">
              이미 존재하는 이메일 입니다.
            </p>
            <p v-else class="text-success">유효한 이메일입니다</p>
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
              :disabled="
                isValidEmail ||
                isDuplicatedEmail ||
                !isVerificationCodeInputStarted
              "
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
              :class="`input input-bordered w-2/3 mr-2 text-sm ${
                isPasswordInputStarted
                  ? !isValidPassword
                    ? 'border-success'
                    : 'border-error'
                  : 'border-gray-300'
              }`"
              placeholder="••••••••"
              class="input input-bordered w-full mr-2 border-gray-300 text-sm"
              required
            />
          </div>
          <div>
            <p v-if="!isPasswordInputStarted"></p>
            <p v-else-if="isValidPassword" class="text-error">
              비밀번호는 4자 이상 20자 이하여야합니다.
            </p>
            <p v-else class="text-success">유효한 비밀번호입니다.</p>
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
              :class="`input input-bordered w-2/3 mr-2 text-sm ${
                isPasswordCheckInputStarted
                  ? !isValidPasswordCheck
                    ? 'border-success'
                    : 'border-error'
                  : 'border-gray-300'
              }`"
              placeholder="••••••••"
              class="input input-bordered w-full mr-2 border-gray-300 text-sm"
              required
            />
          </div>
          <div>
            <p v-if="!isPasswordCheckInputStarted"></p>
            <p v-else-if="isValidPasswordCheck" class="text-error">
              비밀번호가 일치하지 않습니다.
            </p>
            <p v-else class="text-success">비밀번호가 일치합니다.</p>
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
              v-model="nickname"
              :class="`input input-bordered w-2/3 mr-2 text-sm ${
                isNicknameInputStarted
                  ? !isValidNickname && !isDuplicatedNickname
                    ? 'border-success'
                    : 'border-error'
                  : 'border-gray-300'
              }`"
              placeholder="••••••••"
              class="input input-bordered w-full mr-2 border-gray-300 text-sm"
              required
            />
          </div>
          <div>
            <p v-if="!isNicknameInputStarted"></p>
            <p v-else-if="isValidNickname" class="text-error">
              유효하지 않은 닉네임 입니다.
            </p>
            <p v-else-if="isDuplicatedNickname" class="text-error">
              이미 존재하는 닉네임 입니다.
            </p>
            <p v-else class="text-success">유효한 닉네임입니다</p>
          </div>
          <div class="pt-5">
            <button
              @click="registerBtn"
              type="button"
              class="mt-15 btn btn-primary w-full"
              :disabled="
                isValidEmail ||
                isDuplicatedEmail ||
                isValidNickname ||
                isDuplicatedNickname ||
                isValidPassword ||
                isValidPasswordCheck
              "
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
import { ref, watch } from "vue";

import {
  checkEmailDuplicated,
  checkNicknameDuplicated,
  checkVerificationCode,
  createVerificationCode,
  register,
} from "@/api/auth";

const router = useRouter();

const isEmailInputStarted = ref(false);
const isValidEmail = ref(true);
const isDuplicatedEmail = ref(false);
const email = ref("");

const isNicknameInputStarted = ref(false);
const isValidNickname = ref(true);
const isDuplicatedNickname = ref(false);
const nickname = ref("");

const isPasswordInputStarted = ref(false);
const isValidPassword = ref(true);
const password = ref("");

const isVerificationCodeInputStarted = ref(false);
const verificationCode = ref("");

const isPasswordCheckInputStarted = ref(false);
const isValidPasswordCheck = ref(true);
const confirmPassword = ref("");

const clickSendVerificationCodeBtn = async () => {
  let response = await createVerificationCode(email.value);
  alert("인증 코드를 발송했습니다.");
};

const checkEmailDuplication = async () => {
  // 이메일 중복 확인 API를 호출하고 응답을 처리하는 코드가 들어갑니다.
  // 이 예시에서는 결과를 바로 가정합니다.

  let response = await checkEmailDuplicated(email.value); // 실제 API 호출을 적용하세요.

  if (response.data) {
    // 서버 응답에 따라 조건을 수정하세요.
    isDuplicatedEmail.value = true;
  } else {
    isDuplicatedEmail.value = false;
  }
};

watch(email, (newEmail) => {
  isEmailInputStarted.value = newEmail.length > 0;
  const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
  if (emailRegex.test(newEmail)) {
    isValidEmail.value = false;
    checkEmailDuplication();
  } else {
    isValidEmail.value = true;
    isDuplicatedEmail.value = false;
  }
});

watch(verificationCode, (newVerificationCode) => {
  isVerificationCodeInputStarted.value = newVerificationCode.length > 0;
});

const checkNicknameDuplication = async () => {
  let response = await checkNicknameDuplicated(nickname.value); // 실제 API 호출을 적용하세요.

  if (response.data) {
    // 서버 응답에 따라 조건을 수정하세요.
    isDuplicatedNickname.value = true;
  } else {
    isDuplicatedNickname.value = false;
  }
};

watch(nickname, (newNickname) => {
  isNicknameInputStarted.value = newNickname.length > 0;
  if (newNickname.length >= 2 && newNickname.length <= 10) {
    isValidNickname.value = false;
    checkNicknameDuplication();
  } else {
    isValidNickname.value = true;
    isDuplicatedNickname.value = false;
  }
});

watch(password, (newPassword) => {
  isPasswordInputStarted.value = newPassword.length > 0;
  if (newPassword.length >= 4 && newPassword.length <= 20) {
    isValidPassword.value = false;
  } else {
    isValidPassword.value = true;
  }
});

watch(confirmPassword, (newPasswordCheck) => {
  isPasswordCheckInputStarted.value = newPasswordCheck.length > 0;
  if (
    newPasswordCheck.length >= 4 &&
    newPasswordCheck.length <= 20 &&
    newPasswordCheck === password.value
  ) {
    isValidPasswordCheck.value = false;
  } else {
    isValidPasswordCheck.value = true;
  }
});

const checkVerificationCodeBtn = async () => {
  const requestData = {
    email: email.value,
    verificationCode: verificationCode.value,
  };
  let response = await checkVerificationCode(requestData);
  if (response.status === 200) {
    sessionStorage.setItem("email", response.headers.email);
    alert("인증번호 검증이 완료됐습니다.");
  }
};

const registerBtn = async () => {
  const requestData = {
    email: email.value,
    nickname: nickname.value,
    password: password.value,
  };
  let response = await register(sessionStorage.getItem("email"), requestData);

  if (response.status >= 200 && response.status < 300) {
    sessionStorage.removeItem("email");
    alert("회원가입이 완료됐습니다.");
    await router.push("/");
  }
};
</script>