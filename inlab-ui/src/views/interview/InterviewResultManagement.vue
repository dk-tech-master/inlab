<template>
  <header class="mt-8">
    <div class="mb-10">
      <p class="mb-1 text-sm font-light text-gray-500">
        면접 관리 >
        <span class="font-medium text-indigo-500">면접 결과 관리</span>
      </p>
      <h2 class="text-3xl tracking-tight font-bold text-gray-800">
        면접 결과 관리
      </h2>
    </div>
    <div class="mr-3">
      <div class="flex">
        <InputSearchFilter>
          <template v-slot:body>
            <div>
              <label
                for="searchInterview"
                class="block mb-2 text-base font-bold text-gray-700"
                >면접 결과 검색</label
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
    </div>
  </header>
  <section class="mr-16 mt-8">
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
          면접자
        </div>
        <div class="w-[15%] flex flex-col justify-center px-6 py-2 text-left">
          면접관
        </div>
        <div
          class="w-[30%] mx-auto flex flex-col items-center justify-center px-6 py-2 text-left"
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
          신다인
        </div>
        <div
          class="w-[30%] mx-auto flex flex-col items-center justify-center px-6 py-1 text-left"
        >
          <button
            type="button"
            class="flex flex-col items-center ml-3 px-7 py-5 btn btn-outline btn-primary btn-sm hover:bg-indigo-600 hover:text-white"
          >
            결과 확인
          </button>
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
