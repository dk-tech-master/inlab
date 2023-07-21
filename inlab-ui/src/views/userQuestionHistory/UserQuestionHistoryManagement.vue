<template>
  <header>
    <div class="mb-10">
      <p class="mb-4 text-sm font-medium text-indigo-500">질문 열람 내역</p>
      <h2 class="text-3xl tracking-tight font-bold text-gray-800">
        질문 열람 내역
      </h2>
    </div>
    <div class="flex justify-between mb-8">
      <InputSearchFilter>
        <template v-slot:body>
          <div class="flex gap-x-4">
            <div>
              <label
                for="searchInterview"
                class="block mb-2 text-base font-bold text-gray-700"
                >질문 열람 내역 검색</label
              >
              <div class="flex">
                <input
                  type="text"
                  name="interviewTitle"
                  class="w-64 input input-bordered border-gray-300 text-sm"
                  placeholder="열람한 계정 및 질문 제목을 검색하세요"
                  v-model="searchInput"
                  @keyup.enter="handleSearch"
                  required
                />
              </div>
            </div>
            <div class="flex items-end">
              <button
                @click="handleSearch"
                class="btn btn-primary"
              >
                검색
              </button>
            </div>
          </div>
        </template>
      </InputSearchFilter>
    </div>
  </header>
  <section v-if="userQuestionHistoryList.length > 0">
    <div class="table flex flex-col w-full overflow-x-auto sm:rounded-lg">
      <div class="flex items-center bg-gray-50 font-bold text-sm text-gray-800 gap-x-4 px-2">
        <div class="w-[50%] flex flex-col justify-center py-4 text-left">
          질문 제목
        </div>
        <div class="w-[10%] flex flex-col justify-center py-4 text-left">
          현재 버전
        </div>
        <div class="w-[20%] flex flex-col justify-center py-4 text-left">
          열람 계정
        </div>
        <div class="w-[20%] flex flex-col justify-center py-4 text-left">
          열람일
        </div>
      </div>
      <div
        class="flex items-center border-b hover:bg-gray-100 gap-x-4 px-2"
        v-for="(item, index) in userQuestionHistoryList"
        :key="index"
      >
        <div class="w-[50%] py-4 truncate">
          {{ item.questionTitle }}
        </div>
        <div class="w-[10%] flex flex-col justify-center py-4 text-left">
          {{ item.questionVersion }}
        </div>
        <div class="w-[20%] flex flex-col justify-center py-4 text-left">
          {{ item.nickname }}
        </div>
        <div class="w-[20%] flex flex-col justify-center py-4 text-left">
          {{ item.readingTime }}
        </div>
      </div>
    </div>
    <div>
      <Pagination
        v-if="loaded"
        :paging-util="pagingUtil"
        @change-page="changePage"
      />
    </div>
  </section>
  <section v-else class="mr-16 mt-8">
    <div class="border rounded-lg py-20">
      <div class="flex justify-center mb-10">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          stroke-width="1.5"
          stroke="currentColor"
          class="w-20 h-20 text-gray-500"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            d="M21 7.5l-9-5.25L3 7.5m18 0l-9 5.25m9-5.25v9l-9 5.25M3 7.5l9 5.25M3 7.5v9l9 5.25m0-9v9"
          />
        </svg>
      </div>
      <p class="text-lg text-gray-500 text-center">
        질문 열람 내역이 존재하지 않습니다.
      </p>
    </div>
  </section>
</template>

<script setup>
import { ref } from "vue";
import { getUserQuestionHistoryList } from "@/api/userQuestionHistory";
import InputSearchFilter from "@/components/common/InputSearchFilter.vue";
import Pagination from "@/components/common/Pagination.vue";

const loaded = ref(false);
const pagingUtil = ref({});
const userQuestionHistoryList = ref([]);
const searchInput = ref("");

const init = async () => {
  const userQuestionHistoryInfo = await getUserQuestionHistoryList();
  userQuestionHistoryList.value = userQuestionHistoryInfo.data.responseList;
  pagingUtil.value = userQuestionHistoryInfo.data.pagingUtil;
  loaded.value = true;
  console.log(userQuestionHistoryInfo.data);
};

init();

const handleSearch = async () => {
  const requestData = {
    page: 1,
    keyword: searchInput.value,
  };
  const userQuestionHistoryInfo = await getUserQuestionHistoryList(requestData);
  userQuestionHistoryList.value = userQuestionHistoryInfo.data.responseList;
  pagingUtil.value = userQuestionHistoryInfo.data.pagingUtil;
};

const changePage = async (page) => {
  console.log(`changePage ${page}`);
  const requestData = {
    page: page,
    keyword: searchInput.value,
  };
  let response = await getUserQuestionHistoryList(requestData);
  userQuestionHistoryList.value = response.data.responseList;
  pagingUtil.value = response.data.pagingUtil;
};
</script>
