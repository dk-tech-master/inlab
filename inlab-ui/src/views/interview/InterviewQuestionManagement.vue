<template>
  <header class="mt-8">
    <div class="mb-10">
      <p class="mb-1 text-sm font-light text-gray-500">
        면접 관리 > 면접 상세 >
        <span class="font-medium text-indigo-500">면접 질문 관리</span>
      </p>
      <h2 class="text-3xl tracking-tight font-bold text-gray-800">
        면접 질문 관리
      </h2>
    </div>
  </header>
  <section class="mr-16 mt-8 px-10 py-10 shadow-md">
    <div class="flex justify-between">
      <h2 class="mb-10 text-2xl font-bold">질문 검색</h2>
    </div>
    <div class="flex">
      <div class="mr-5 mb-5">
        <VSearchFilter />
      </div>
    </div>

    <div class="mt-3 table flex flex-col w-full overflow-x-auto sm:rounded-lg">
      <div class="flex bg-gray-50 font-bold text-sm text-gray-800">
        <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
          No
        </div>
        <div class="w-[25%] flex flex-col justify-center px-6 py-2 text-left">
          질문 제목
        </div>
        <div class="w-[20%] flex flex-col justify-center px-6 py-2 text-left">
          직무
        </div>
        <div class="w-[30%] flex flex-col justify-center px-6 py-2 text-left">
          유형
        </div>
        <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
          난이도
        </div>
        <div
          class="w-[15%] flex flex-col items-center justify-center px-6 py-2 text-left"
        ></div>
      </div>
      <div
        class="flex border-b hover:bg-gray-100"
        v-for="(index, item) in 5"
        :key="index"
      >
        <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
          {{ index }}
        </div>
        <div class="w-[25%] flex flex-col justify-center px-6 py-2 text-left">
          Java의 정의
        </div>
        <div class="w-[20%] flex flex-col justify-center px-6 py-2 text-left">
          프론트앤드
        </div>
        <div class="w-[30%] flex flex-col justify-center px-6 py-2 text-left">
          기술적인 지식 및 개념 질문
        </div>
        <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
          1
        </div>
        <div class="w-[15%] flex flex-col justify-center px-6 py-2 text-left">
          <button class="customBtn">추가</button>
        </div>
      </div>
    </div>
    <Pagination />
  </section>

  <section class="mr-16 mt-8 px-10 py-12 shadow-md">
    <div class="flex justify-between">
      <h2 class="mb-10 text-2xl font-bold">면접 질문 리스트</h2>
    </div>

    <div class="mt-3 table flex flex-col w-full overflow-x-auto sm:rounded-lg">
      <div class="flex bg-gray-50 font-bold text-sm text-gray-800">
        <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
          No
        </div>
        <div class="w-[30%] flex flex-col justify-center px-6 py-2 text-left">
          질문 제목
        </div>
        <div class="w-[20%] flex flex-col justify-center px-6 py-2 text-left">
          직무
        </div>
        <div class="w-[30%] flex flex-col justify-center px-6 py-2 text-left">
          유형
        </div>
        <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
          난이도
        </div>
        <div
          class="w-[10%] flex flex-col items-center justify-center px-6 py-2 text-left"
        ></div>
      </div>
      <div
        class="flex border-b hover:bg-gray-100"
        v-for="(index, item) in 5"
        :key="index"
      >
        <div class="w-[10%] flex flex-col justify-center px-6 py-4 text-left">
          {{ index }}
        </div>
        <div class="w-[30%] flex flex-col justify-center px-6 py-4 text-left">
          Java의 정의
        </div>
        <div class="w-[20%] flex flex-col justify-center px-6 py-4 text-left">
          프론트앤드
        </div>
        <div class="w-[30%] flex flex-col justify-center px-6 py-4 text-left">
          기술적인 지식 및 개념 질문
        </div>
        <div class="w-[10%] flex flex-col justify-center px-6 py-4 text-left">
          1
        </div>
        <div class="w-[10%] flex flex-col justify-center px-6 py-4 text-left">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="w-6 h-6 cursor-pointer"
            @click="deleteInterviewQuestion(index)"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0"
            />
          </svg>
        </div>
      </div>
    </div>
    <Pagination />
  </section>
</template>

<script setup>
import Pagination from "@/components/common/Pagination.vue";
import VSearchFilter from "@/components/common/SearchFilter.vue";
</script>
<style scoped></style>
