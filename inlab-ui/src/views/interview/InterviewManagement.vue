<template>
  <header>
    <div class="mb-10">
      <p class="mb-4 text-sm font-light text-gray-500">
        <span class="font-medium text-indigo-500">면접 관리</span>
      </p>
      <h2 class="text-3xl tracking-tight font-bold text-gray-800">면접 관리</h2>
    </div>
    <div class="flex justify-between mb-8">
      <InputSearchFilter>
        <template v-slot:body>
          <div class="flex gap-x-4">
            <div>
              <label
                for="searchInterview"
                class="block mb-2 text-base font-bold text-gray-700"
                >면접 검색</label
              >
              <div class="flex">
                <input
                  type="text"
                  name="interviewTitle"
                  class="w-64 input input-bordered border-gray-300 text-sm"
                  placeholder="면접 제목을 입력하세요"
                  v-model="interviewTitle"
                  @keyup.enter="clickSearchInterviewBtn"
                  required
                />
              </div>
            </div>
            <div class="flex self-end">
              <button @click="clickSearchInterviewBtn" class="btn btn-primary">
                검색
              </button>
            </div>
          </div>
          <button
            @click="clickCreateInterviewBtn"
            class="btn btn-primary self-end"
          >
            면접 추가
          </button>
        </template>
        <template v-slot:footer />
      </InputSearchFilter>
    </div>
  </header>
  <section>
    <div class="table flex flex-col w-full overflow-x-auto sm:rounded-lg">
      <div class="flex items-center bg-gray-50 font-bold text-sm text-gray-800 gap-x-4 px-2">
        <div class="w-[45%] flex flex-col justify-center py-4 text-left">
          면접 제목
        </div>
        <div class="w-[25%] flex flex-col justify-center py-4 text-left">
          면접관
        </div>
        <div class="w-[10%] flex flex-col justify-center py-4 text-left">
          질문 개수
        </div>
        <div class="w-[10%] flex flex-col justify-center py-4 text-left">
          수정
        </div>
        <div
          class="w-[10%] mx-auto flex flex-col justify-center py-4 text-left"
        >
          면접 시작
        </div>
      </div>
      <div
        class="flex items-center border-b hover:bg-gray-100 gap-x-4 px-2"
        v-for="(interview, index) in interviewList"
        :key="index"
        @click="clickDetail(interview.interviewId)"
      >
        <div
          class="w-[45%] py-4 truncate"
        >
          {{ interview.interviewTitle }}
        </div>
        <div class="w-[25%] flex flex-col justify-center py-4 text-left">
          {{ interview.nickname }}
        </div>
        <div class="w-[10%] flex flex-col justify-center py-4 text-left">
          {{ interview.questionCount }}
        </div>
        <div class="w-[10%] flex flex-col justify-center py-4 text-left">
          <button type="button" @click.stop="clickUpdateInterviewBtn(index)">
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
          </button>
        </div>
        <div
          class="w-[10%] flex flex-col justify-center py-4 text-left"
        >
          <button
            type="button"
            class="btn btn-neutral btn-sm"
            @click.stop="clickInterviewStartBtn(interview.interviewId)"
          >
            면접 시작
          </button>
        </div>
      </div>
    </div>
  </section>
  <section>
    <Pagination
      v-if="loaded"
      :paging-util="pagingUtil"
      @change-page="changePage"
    />
  </section>
  <CreateInterviewModal ref="createInterviewModal" @init="init" />
  <UpdateInterviewModal ref="updateInterviewModal" @init="init" />
</template>

<script setup>
import Pagination from "@/components/common/Pagination.vue";
import InputSearchFilter from "@/components/common/InputSearchFilter.vue";
import { getInterviewList } from "@/api/interview";
import { ref } from "vue";
import CreateInterviewModal from "@/components/modal/CreateInterviewModal.vue";
import UpdateInterviewModal from "@/components/modal/UpdateInterviewModal.vue";
import router from "@/router";

const interviewList = ref([]);
const pagingUtil = ref({});
const interviewTitle = ref("");
const nickname = ref("");
const createInterviewModal = ref(null);
const updateInterviewModal = ref(null);
const loaded = ref(false);

const init = async () => {
  const requestData = {
    interviewTitle: interviewTitle.value,
    nickname: nickname.value,
  };
  const response = await getInterviewList(
    requestData,
    sessionStorage.getItem("userId"),
  );
  interviewList.value = response.data.responseList;
  pagingUtil.value = response.data.pagingUtil;
  loaded.value = true;
};

init();

const clickCreateInterviewBtn = () => {
  createInterviewModal.value.toggleModal();
};

const clickUpdateInterviewBtn = (index) => {
  const interviewData = interviewList.value[index];
  updateInterviewModal.value.toggleModal(interviewData);
};

const clickSearchInterviewBtn = async () => {
  const requestData = {
    interviewTitle: interviewTitle.value,
    nickname: nickname.value,
  };
  const response = await getInterviewList(
    requestData,
    sessionStorage.getItem("userId"),
  );
  interviewList.value = response.data.responseList;
  pagingUtil.value = response.data.pagingUtil;
};

const changePage = async (page) => {
  const requestData = {
    interviewTitle: interviewTitle.value,
    nickname: nickname.value,
    page: page,
  };
  let response = await getInterviewList(
    requestData,
    sessionStorage.getItem("userId"),
  );
  interviewList.value = response.data.responseList;
  pagingUtil.value = response.data.pagingUtil;
};
const clickDetail = (interviewId) => {
  router.push(`/interview/detail/${interviewId}`);
};

const clickInterviewStartBtn = (interviewId) => {
  router.push(`/interview/start/${interviewId}`);
};
</script>
<style scoped></style>
