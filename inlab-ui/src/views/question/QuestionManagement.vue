<template>
  <header class="mt-8">
    <div class="mb-10">
      <p class="mb-1 text-sm font-light text-gray-500">질문 관리</p>
      <h2 class="text-3xl tracking-tight font-bold text-gray-800">질문 관리</h2>
    </div>
  </header>
  <section class="mr-16 mt-8 px-10 py-10 shadow-md">
    <div class="mt-3 table flex flex-col w-full overflow-x-auto sm:rounded-lg">
      <div class="flex bg-gray-50 font-bold text-sm text-gray-800">
        <div class="w-[5%] flex flex-col justify-center px-6 py-2 text-left">
          No
        </div>
        <div class="w-[20%] flex flex-col justify-center px-6 py-2 text-left">
          질문 제목
        </div>
        <div class="w-[12%] flex flex-col justify-center px-6 py-2 text-left">
          직무
        </div>
        <div class="w-[18%] flex flex-col justify-center px-6 py-2 text-left">
          유형
        </div>
        <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
          난이도
        </div>
        <div
          class="w-[8%] flex flex-col items-center justify-center px-6 py-2 text-left"
        >
          버전
        </div>
        <div
          class="w-[11%] flex flex-col items-center justify-center px-6 py-2 text-left"
        >
          꼬리질문
        </div>
        <div
          class="w-[8%] flex flex-col items-center justify-center px-6 py-2 text-left"
        >
          수정
        </div>
        <div
          class="w-[8%] flex flex-col items-center justify-center px-6 py-2 text-left"
        >
          삭제
        </div>
      </div>
      <div
        class="flex border-b hover:bg-gray-100"
        v-for="(index, item) in 5"
        :key="index"
        @click="showModal(index)"
      >
        <div class="w-[5%] flex flex-col justify-center px-6 py-2 text-left">
          {{ index }}
        </div>
        <div class="w-[20%] flex flex-col justify-center px-6 py-2 text-left">
          Java의 정의
        </div>
        <div class="w-[12%] flex flex-col justify-center px-6 py-2 text-left">
          프론트앤드
        </div>
        <div class="w-[18%] flex flex-col justify-center px-6 py-2 text-left">
          기술적인 지식 및 개념 질문
        </div>
        <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
          1
        </div>
        <div class="w-[8%] flex flex-col justify-center px-6 py-2 text-left">
          1
        </div>
        <div class="w-[11%] flex flex-col justify-center px-6 py-2 text-left">
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
              d="M17.593 3.322c1.1.128 1.907 1.077 1.907 2.185V21L12 17.25 4.5 21V5.507c0-1.108.806-2.057 1.907-2.185a48.507 48.507 0 0111.186 0z"
            />
          </svg>
        </div>
        <div class="w-[8%] flex flex-col justify-center px-6 py-2 text-left">
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
        <div class="w-[8%] flex flex-col justify-center px-6 py-2 text-left">
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
              d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0"
            />
          </svg>
        </div>
      </div>
    </div>
    <Pagination />
    <label for="my_modal_7" class="btn">open modal</label>
  </section>

  <!--상세 모달-->
  <VQuestionManagement />
</template>

<script setup>
import Pagination from "@/components/common/Pagination.vue";
import VQuestionManagement from "@/components/teleport/VQuestionManagement.vue";
</script>

<style scoped></style>
