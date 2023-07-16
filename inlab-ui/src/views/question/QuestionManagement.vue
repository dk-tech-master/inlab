<template>
  <header class="mt-8">
    <div class="mb-10">
      <p class="mb-1 text-sm font-light text-gray-500">질문 관리</p>
      <h2 class="text-3xl tracking-tight font-bold text-gray-800">질문 관리</h2>
    </div>
  </header>
  <SearchFilter @search-info="handleSearch" />
  <section class="mr-16 mt-8 px-10 py-10 shadow-md">
    <div class="mt-3 table flex flex-col w-full overflow-x-auto sm:rounded-lg">
      <div class="flex bg-gray-50 font-bold text-sm text-gray-800">
        <div class="w-[5%] flex flex-col justify-center px-6 py-2 text-left">
          No
        </div>
        <div class="w-[28%] flex flex-col justify-center px-6 py-2 text-left">
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
      </div>
      <div
        class="flex border-b hover:bg-gray-100 cursor-pointer"
        v-for="(question, index) in questionList"
        :key="index"
        @click="showModal(index)"
      >
        <div class="w-[5%] flex flex-col justify-center px-6 py-2 text-left">
          {{ index + 1 }}
        </div>
        <div class="w-[28%] flex flex-col justify-center px-6 py-2 text-left">
          {{ question.title }}
        </div>
        <div class="w-[12%] flex flex-col justify-center px-6 py-2 text-left">
          {{ question.positionName }}
        </div>
        <div class="w-[18%] flex flex-col justify-center px-6 py-2 text-left">
          {{ question.questionTypeName }}
        </div>
        <div class="w-[10%] flex flex-col justify-center px-6 py-2 text-left">
          {{ question.questionLevelId }}
        </div>
        <div class="w-[8%] flex flex-col justify-center px-6 py-2 text-left">
          {{ question.version }}
        </div>
        <div class="w-[11%] flex flex-col justify-center px-6 py-2 text-left">
          <a href="/">
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
          </a>
        </div>
        <div class="w-[8%] flex flex-col justify-center px-6 py-2 text-left">
          <a href="">
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
          </a>
        </div>
      </div>
    </div>
    <!--    <Pagination />-->
    <label for="my_modal_7" class="btn">open modal</label>
  </section>

  <!--상세 모달-->
  <VQuestionManagement />
</template>

<script setup>
import Pagination from "@/components/common/Pagination.vue";
import VQuestionManagement from "@/components/teleport/VQuestionManagement.vue";
import SearchFilter from "@/components/common/SearchFilter.vue";
import { getQuestion } from "@/api/question";
import { ref } from "vue";

const pagingInfo = ref({
  page: 1,
});

const pagingUtil = ref({});
const questionList = ref([]);
const init = async () => {
  const questionsInfo = await getQuestion(pagingInfo.value);
  questionList.value = questionsInfo.data.responseList;
  console.log(questionsInfo.data);
  pagingUtil.value = questionsInfo.data.pagingUtil;
};

init();

const handleSearch = async (searchInfo) => {
  const requestData = {
    page: pagingInfo.value.page,
    pageSize: pagingInfo.value.pageSize,
    positionId: searchInfo.position,
    questionLevelId: searchInfo.level,
    questionTypeId: searchInfo.type,
    titleKeyword: searchInfo.title,
  };
  const searchedInfo = await getQuestion(requestData);
  questionList.value = searchedInfo.data.responseList;
};
</script>

<style scoped></style>
