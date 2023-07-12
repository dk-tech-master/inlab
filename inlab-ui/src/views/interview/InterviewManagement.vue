<template>
  <header class="mt-8">
    <div class="mb-10">
      <p class="mb-1 text-sm font-medium text-indigo-500">면접 관리</p>
      <h2 class="text-3xl tracking-tight font-bold text-gray-800">면접 관리</h2>
    </div>
    <div class="flex">
      <InputSearchFilter>
        <template v-slot:body>
          <div>
            <label
              for="searchInterview"
              class="block mb-2 text-base font-bold text-gray-700"
              >면접 검색</label
            >
            <input
              type="text"
              name="interviewTitle"
              class="py-5 pl-7 pr-36 bg-gray-50 input input-bordered input-sm border-gray-300 text-sm"
              placeholder="면접 제목을 입력하세요"
              required
            />
          </div>
        </template>
        <template v-slot:footer />
      </InputSearchFilter>
    </div>
  </header>
  <section class="mr-16 mt-8">
    <button
      @click="testRequest"
      class="btn btn-primary btn-sm ml-auto flex flex-col items-center ml-3 px-7 py-5"
    >
      test-button
    </button>
    <button
      class="btn btn-primary btn-sm ml-auto flex flex-col items-center ml-3 px-7 py-5"
    >
      면접 추가
    </button>
    <div class="mt-3 table flex flex-col w-full overflow-x-auto sm:rounded-lg">
      <div class="flex bg-gray-50 font-bold text-sm text-gray-800">
        <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
          No
        </div>
        <div class="w-[30%] flex flex-col justify-center px-6 py-2 text-left">
          면접 제목
        </div>
        <div class="w-[15%] flex flex-col justify-center px-6 py-2 text-left">
          면접관
        </div>
        <div class="w-[15%] flex flex-col justify-center px-6 py-2 text-left">
          질문 개수
        </div>
        <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
          수정
        </div>
        <div
          class="w-[20%] mx-auto flex flex-col items-center justify-center px-6 py-2 text-left"
        ></div>
      </div>
      <div
        class="flex border-b hover:bg-gray-100"
        v-for="(index, item) in 10"
        :key="index"
      >
        <div class="w-[10%] flex flex-col justify-center px-6 py-4 text-left">
          {{ index }}
        </div>
        <div class="w-[30%] flex flex-col justify-center px-6 py-4 text-left">
          Java의 정의
        </div>
        <div class="w-[15%] flex flex-col justify-center px-6 py-4 text-left">
          burpee
        </div>
        <div class="w-[15%] flex flex-col justify-center px-6 py-4 text-left">
          10
        </div>
        <div class="w-[10%] flex flex-col justify-center px-6 py-4 text-left">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="w-6 h-6"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10"
            />
          </svg>
        </div>
        <div
          class="w-[20%] mx-auto flex flex-col items-center justify-center px-6 py-1 text-left"
        >
          <button
            type="button"
            class="flex flex-col items-center ml-3 px-7 py-5 btn btn-outline btn-primary btn-sm hover:bg-indigo-600 hover:text-white"
          >
            면접 시작
          </button>
        </div>
      </div>
    </div>
  </section>
  <section class="ml-72 mr-10 mt-20 mb-32">
    <Pagination />
  </section>

  <!--수정 조건문으로 구현-->
  <teleport to="teleport-area">
    <VModal>
      <template v-slot:header>
        <h2 class="mb-6 text-xl font-semibold tracking-tight">면접 등록</h2>
      </template>
      <template v-slot:body>
        <input
          type="text"
          name="registerJob"
          placeholder="면접 제목을 입력하세요"
          class="input input-bordered w-full mr-2 border-gray-300 text-sm"
          required
        />
      </template>
      <template v-slot:footer>
        <div class="flex justify-end mt-12">
          <button
            class="flex flex-col mr-3 py-5 px-7 btn btn-sm btn-primary btn-outline"
          >
            취소
          </button>
          <button class="flex flex-col py-5 px-7 btn btn-sm btn-primary">
            등록
          </button>
        </div>
      </template>
    </VModal>
  </teleport>
</template>

<script setup>
import Pagination from "@/components/common/Pagination.vue";
import InputSearchFilter from "@/components/common/InputSearchFilter.vue";
import { createVerificationCode } from "@/api/auth";
const testRequest = async () => {
  const test = "wkdudsnrn@naver.com";
  await createVerificationCode(test);
};
</script>
<style scoped></style>
