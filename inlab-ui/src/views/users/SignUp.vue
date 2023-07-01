<script setup></script>

<template>
  <section>
    <div
      class="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0"
    >
      <div class="w-full rounded-lg md:mt-0 sm:max-w-md xl:p-0">
        <h1
          class="mb-12 text-center text-3xl font-bold tracking-tight text-gray-800"
        >
          회원가입
        </h1>
        <form class="space-y-4 md:space-y-6" action="#">
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
                id="email"
                class="input input-bordered w-2/3 mr-2 border-gray-300 text-sm"
                placeholder="user@dktechin.com"
                required=""
              />
              <button type="submit" class="btn basicBtn w-1/3">
                인증번호발송
              </button>
            </div>
          </div>

          <div class="mb-4 flex justify-between">
            <input
              type="email"
              name="email"
              id="email"
              class="input input-bordered w-2/3 mr-2 border-gray-300 text-sm"
              placeholder="user@dktechin.com"
              required=""
            />
            <button type="submit" class="btn basicOutlineBtn w-1/3">
              인증번호 확인
            </button>
          </div>

          <div>
            <label
              for="password"
              class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >비밀번호</label
            >
            <input
              type="password"
              name="password"
              id="password"
              placeholder="••••••••"
              class="input input-bordered w-full mr-2 border-gray-300 text-sm"
              required=""
            />
          </div>
          <div>
            <label
              for="password"
              class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >비밀번호 확인</label
            >
            <input
              type="confirm-password"
              name="confirm-password"
              id="confirm-password"
              placeholder="••••••••"
              class="input input-bordered w-full mr-2 border-gray-300 text-sm"
              required=""
            />
          </div>

          <div>
            <label
              for="nickname"
              class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >닉네임</label
            >
            <input
              type="confirm-password"
              name="confirm-password"
              id="confirm-password"
              placeholder="••••••••"
              class="input input-bordered w-full mr-2 border-gray-300 text-sm"
              required=""
            />
          </div>

          <div class="pt-5">
            <button type="submit" class="mt-15 btn basicBtn">회원가입</button>
            <p class="mt-3 text-center text-sm font-light text-gray-500">
              계정이 벌써 있으신가요?
              <a href="#" class="font-medium text-indigo-500 hover:underline"
                >로그인 바로가기</a
              >
            </p>
          </div>
        </form>
      </div>
    </div>
  </section>
</template>

<style scoped></style>
