<template>
  <header>
    <div class="mb-10">
      <p class="text-sm text-indigo-500 mb-4 font-medium">질문 관리</p>
      <h2 class="text-3xl tracking-tight font-bold text-gray-800">질문 관리</h2>
    </div>
    <div class="flex justify-between mb-8">
      <SearchFilter @search-info="handleSearch" />
      <div class="self-end">
        <label
          for="registerModal"
          class="px-6 btn btn-primary"
          @click="openRegisterModal"
          >등록</label
        >
      </div>
    </div>
  </header>
  <section v-if="questionList.length > 0">
    <div class="table flex flex-col w-full overflow-x-auto sm:rounded-lg">
      <div
        class="flex items-center bg-gray-50 font-bold text-sm text-gray-800 gap-x-4 px-2"
      >
        <div class="w-[45%] flex flex-col justify-center py-4 text-left">
          질문 제목
        </div>
        <div class="w-[15%] flex flex-col justify-center py-4 text-left">
          직무
        </div>
        <div class="w-[15%] flex flex-col justify-center py-4 text-left">
          유형
        </div>
        <div class="w-[5%] flex flex-col justify-center py-4 text-left">
          난이도
        </div>
        <div class="w-[5%] flex flex-col justify-center py-4 text-left">
          버전
        </div>
        <div class="w-[5%] flex flex-col justify-center py-4 text-left">
          버전관리
        </div>
        <div class="w-[5%] flex flex-col justify-center py-4 text-left">
          꼬리질문
        </div>
        <div class="w-[5%] flex flex-col justify-center py-4 text-left">
          수정
        </div>
      </div>

      <label
        for="detailViewModal"
        class="flex items-center border-b hover:bg-gray-100 cursor-pointer gap-x-4 px-2"
        v-for="question in questionList"
        :key="question.title"
        @click="openDetailViewModal(question.questionId)"
      >
        <div class="w-[45%] py-4 truncate">
          {{ question.title }}
        </div>
        <div class="w-[15%] flex flex-col justify-center py-4 text-left">
          {{ question.positionName }}
        </div>
        <div class="w-[15%] flex flex-col justify-center py-4 text-left">
          {{ question.questionTypeName }}
        </div>
        <div class="w-[5%] flex flex-col justify-center py-4 text-left">
          {{ question.questionLevelName }}
        </div>
        <div class="w-[5%] flex flex-col justify-center py-4 text-left">
          {{ question.version }}
        </div>
        <div class="w-[5%] flex flex-col justify-center py-4 text-left">
          <router-link :to="`/question/version/${question?.questionId}`">
            <label class="flex cursor-pointer justify-start">
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
            </label>
          </router-link>
        </div>
        <div class="w-[5%] flex flex-col justify-center py-4 text-left">
          <router-link
            :to="`/question/follow-up/${question?.questionId}`"
            @click="store.setQuestionTitle(question.title)"
          >
            <label class="flex cursor-pointer justify-start">
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
                  d="M16.5 3.75V16.5L12 14.25 7.5 16.5V3.75m9 0H18A2.25 2.25 0 0120.25 6v12A2.25 2.25 0 0118 20.25H6A2.25 2.25 0 013.75 18V6A2.25 2.25 0 016 3.75h1.5m9 0h-9"
                />
              </svg>
            </label>
          </router-link>
        </div>
        <div class="w-[5%] flex flex-col justify-center py-4 text-left">
          <label
            for="updateModal"
            class="flex cursor-pointer justify-start"
            :key="question.title"
            @click="openUpdateModal(question.questionId)"
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
                d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10"
              />
            </svg>
          </label>
        </div>
      </label>
    </div>
    <div>
      <Pagination :paging-util="pagingUtil" @changePage="changePage" />
    </div>
  </section>
  <EmptyState v-else :message="'질문이 존재하지 않습니다.'" />
  <QuestionDetailModal ref="detailViewModal" />
  <QuestionRegistrationModal ref="registerModal" @init="init" />
  <QuestionUpdateModal
    ref="updateModal"
    :questionId="questionId"
    @init="init"
  />
</template>

<script setup>
import Pagination from "@/components/common/Pagination.vue";
import SearchFilter from "@/components/common/SearchFilter.vue";
import { getQuestion } from "@/api/question";
import { ref } from "vue";
import { authStore } from "@/stores/auth";
import QuestionRegistrationModal from "@/components/modal/QuestionRegistrationModal.vue";
import QuestionUpdateModal from "@/components/modal/QuestionUpdateModal.vue";
import QuestionDetailModal from "@/components/modal/QuestionDetailModal.vue";
import EmptyState from "@/components/common/EmptyState.vue";

const store = authStore();

const registerModal = ref(null);
const detailViewModal = ref(null);
const updateModal = ref(null);
const questionId = ref();

const openRegisterModal = () => {
  registerModal.value.toggleModal();
};

const openDetailViewModal = (id) => {
  detailViewModal.value.toggleModal(id);
};

const openUpdateModal = (id) => {
  updateModal.value.openModal(id);
};

const pagingInfos = ref({
  page: 1,
  pageSize: 7,
});

const pagingUtil = ref({});
const questionList = ref([]);

const init = async () => {
  const questionsInfos = await getQuestion(pagingInfos.value);
  questionList.value = questionsInfos.data.responseList;
  pagingUtil.value = questionsInfos.data.pagingUtil;
  console.log(questionList.value);
};

init();

const requestDate = ref({
  page: 1,
  pageSize: 7,
  positionId: null,
  questionLevelId: null,
  questionTypeId: null,
  titleKeyword: null,
});

const handleSearch = async (searchInfos) => {
  const inputRequest = {
    pageSize: pagingUtil.value.pageSize,
    positionId: searchInfos.position,
    questionLevelId: searchInfos.level,
    questionTypeId: searchInfos.type,
    titleKeyword: searchInfos.title,
  };
  requestDate.value = inputRequest;
  console.log(requestDate.value);
  const searchedInfos = await getQuestion(requestDate.value);
  questionList.value = searchedInfos.data.responseList;
  pagingUtil.value = searchedInfos.data.pagingUtil;
};

const changePage = async (page) => {
  pagingUtil.value.page = page;
  requestDate.value.page = page;
  const questionsInfos = await getQuestion(requestDate.value);
  questionList.value = questionsInfos.data.responseList;
  console.log(questionsInfos.data);
  pagingUtil.value = questionsInfos.data.pagingUtil;
};
</script>

<style scoped></style>