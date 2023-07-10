<template>
  <header class="mt-8">
    <div class="mb-10">
      <p class="mb-1 text-sm font-light text-gray-500">
        질문 관리 >
        <span class="font-medium text-indigo-500">질문 버전 관리</span>
      </p>
      <h2 class="text-3xl tracking-tight font-bold text-gray-800">
        질문 버전 관리
      </h2>
    </div>
  </header>
  <section class="mr-16 mt-8">
    <div class="flex mb-10">
      <InputSearchFilter>
        <template v-slot:body>
          <div>
            <label
              for="searchInterview"
              class="block mb-2 text-base font-bold text-gray-700"
              >질문 검색</label
            >
            <input
              type="text"
              name="interviewTitle"
              class="py-5 pl-7 pr-36 bg-gray-50 input input-bordered input-sm border-gray-300 text-sm"
              placeholder="질문 제목을 입력하세요"
              required
            />
          </div>
        </template>
        <template v-slot:footer />
      </InputSearchFilter>
    </div>
    <div class="mt-3 table flex flex-col w-full overflow-x-auto sm:rounded-lg">
      <div class="flex bg-gray-50 font-bold text-sm text-gray-800">
        <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
          No
        </div>
        <div class="w-[30%] flex flex-col justify-center px-6 py-2 text-left">
          면접 제목
        </div>
        <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
          직무
        </div>
        <div class="w-[20%] flex flex-col justify-center px-6 py-2 text-left">
          유형
        </div>
        <div
          class="w-[15%] mx-auto flex flex-col items-center justify-center px-6 py-2 text-left"
        >
          난이도
        </div>
        <div
          class="w-[15%] mx-auto flex flex-col items-center justify-center px-6 py-2 text-left"
        >
          버전
        </div>
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
        <div class="w-[10%] flex flex-col justify-center px-6 py-4 text-left">
          백엔드
        </div>
        <div class="w-[20%] flex flex-col justify-center px-6 py-4 text-left">
          기술적인 지식 및 개념 질문
        </div>
        <div
          class="w-[15%] mx-auto flex flex-col items-center justify-center px-6 py-1 text-left"
        >
          1
        </div>
        <div
          class="w-[15%] mx-auto flex flex-col items-center justify-center px-6 py-1 text-left"
        >
          7
        </div>
      </div>
    </div>
  </section>
  <section class="ml-72 mr-10 mt-20 mb-32">
    <Pagination />
  </section>
</template>

<script setup>
import Pagination from "@/components/common/Pagination.vue";
import InputSearchFilter from "@/components/common/InputSearchFilter.vue";
</script>
<style scoped></style>
