<template>
  <header class="mt-8">
    <div class="mb-10">
      <p class="mb-1 text-sm font-light text-gray-500">
        질문 카테고리 관리 >
        <span class="font-medium text-indigo-500">직무 관리</span>
      </p>
      <h2 class="text-3xl tracking-tight font-bold text-gray-800">직무 관리</h2>
    </div>
    <div class="flex">
      <InputSearchFilter>
        <template v-slot:body>
          <div>
            <label
              for="searchInterview"
              class="block mb-2 text-base font-bold text-gray-700"
              >직무 검색</label
            >
            <input
              type="text"
              name="interviewTitle"
              class="w-[20rem] py-5 pl-7 bg-gray-50 input input-bordered input-sm border-gray-300 text-sm"
              placeholder="면접의 직무를 검색하세요"
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
      class="btn btn-primary btn-sm ml-auto flex flex-col items-center ml-3 px-7 py-5"
    >
      직무 등록
    </button>
    <div class="mt-3 table flex flex-col w-full overflow-x-auto sm:rounded-lg">
      <div class="flex bg-gray-50 font-bold text-sm text-gray-800">
        <div class="w-[15%] flex flex-col justify-center px-6 py-2 text-left">
          No
        </div>
        <div class="w-[35%] flex flex-col justify-center px-6 py-2 text-left">
          직무
        </div>
        <div class="w-[20%] flex flex-col justify-center px-6 py-2 text-left">
          질문 갯수
        </div>
        <div class="w-[15%] flex flex-col justify-center px-6 py-2 text-left">
          수정
        </div>
        <div
          class="w-[15%] mx-auto flex flex-col items-center justify-center px-6 py-2 text-left"
        >
          삭제
        </div>
      </div>
      <div
        class="flex border-b hover:bg-gray-100"
        v-for="(index, item) in 10"
        :key="index"
      >
        <div class="w-[15%] flex flex-col justify-center px-6 py-4 text-left">
          {{ index }}
        </div>
        <div class="w-[35%] flex flex-col justify-center px-6 py-4 text-left">
          프론트엔드 개발자
        </div>
        <div class="w-[20%] flex flex-col justify-center px-6 py-4 text-left">
          10
        </div>
        <div class="w-[15%] flex flex-col justify-center px-6 py-4 text-left">
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
          class="w-[15%] mx-auto flex flex-col items-center justify-center px-6 py-1 text-left"
        >
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
  </section>
  <section class="ml-72 mr-10 mt-20 mb-32">
    <Pagination />
  </section>

  <teleport to="teleport-area">
    <VModal>
      <template v-slot:header>
        <h2 class="mb-6 text-xl font-semibold tracking-tight">직무 등록</h2>
      </template>
      <template v-slot:body>
        <input
          type="text"
          name="registerJob"
          placeholder="새로운 직무를 입력하세요"
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
            확인
          </button>
        </div>
      </template>
    </VModal>
  </teleport>
</template>

<script setup>
import Pagination from "@/components/common/Pagination.vue";
import InputSearchFilter from "@/components/common/InputSearchFilter.vue";
</script>
<style scoped></style>
