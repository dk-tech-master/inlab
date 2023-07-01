<script setup></script>

<template>
  <section class="box-border">
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
                id="email"
                class="input input-bordered border-gray-300 w-full text-sm"
                placeholder="user@dktechin.com"
                required=""
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
                id="password"
                placeholder="비밀번호 입력"
                class="input input-bordered border-gray-300 w-full text-sm"
                required=""
              />
            </div>
            <div class="flex justify-end">
              <a
                href="#"
                class="mt-2 text-sm font-medium text-indigo-600 hover:underline"
                >비밀번호를 변경하시겠습니까?</a
              >
            </div>
            <button type="submit" class="btn basicBtn mt-10">로그인</button>
            <button type="submit" class="btn basicOutlineBtn mt-2">
              회원가입
            </button>
          </form>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped></style>
