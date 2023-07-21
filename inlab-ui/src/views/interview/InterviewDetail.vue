<template>
  <header>
    <div class="mb-10">
      <p class="mb-4 text-sm font-light text-gray-500">
        면접 관리 > <span class="font-medium text-indigo-500">면접 상세</span>
      </p>
      <h2 class="text-3xl tracking-tight font-bold text-gray-800">면접 상세</h2>
    </div>
  </header>
  <section class="p-8 shadow border rounded-lg mb-8">
    <div class="flex justify-between">
      <h2 class="text-2xl font-bold mb-4">면접 정보</h2>
      <div class="flex gap-x-4">
        <button
          class="btn btn-primary btn-sm"
          @click.stop="clickInterviewStartBtn(interviewInfo.interviewId)"
        >
          면접 시작
        </button>
        <button
          class="btn btn-outline btn-primary btn-sm"
          @click="clickUpdateInterviewBtn"
        >
          정보 수정
        </button>
      </div>
    </div>
    <div class="flex">
      <div class="mr-4">
        <label
          for="interviewer"
          class="block mb-2 text-base font-bold text-gray-700"
          >면접관</label
        >
        <div
          class="flex items-center py-6 pl-5 w-48 bg-gray-50 input input-sm input-bordered text-base text-primary"
        >
          {{ interviewInfo.interviewerNickname }}
        </div>
      </div>
      <div class="mr-4">
        <label
          for="nickName"
          class="block mb-2 text-base font-bold text-gray-700"
          >면접 제목</label
        >
        <div
          class="flex items-center py-6 pl-5 w-96 bg-gray-50 input input-sm input-bordered text-base text-primary"
        >
          {{ interviewInfo.interviewTitle }}
        </div>
      </div>
      <div class="mr-4">
        <label
          for="nickName"
          class="block mb-2 text-base font-bold text-gray-700"
          >질문 개수</label
        >
        <div
          class="flex items-center py-6 pl-5 w-30 bg-gray-50 input input-sm input-bordered text-base text-primary"
        >
          {{ interviewInfo.questionCount }}
        </div>
      </div>
      <div class="mr-4">
        <label
          for="nickName"
          class="block mb-2 text-base font-bold text-gray-700"
          >생성일</label
        >
        <div
          class="flex items-center py-6 pl-5 w-52 bg-gray-50 input input-sm input-bordered text-base text-primary"
        >
          {{ interviewInfo.createdAt.split("T")[0] }}
        </div>
      </div>
    </div>
  </section>
  <section>
    <div class="flex justify-between items-center mb-8">
      <h2 class="text-2xl font-bold">면접 질문 리스트</h2>
      <button
        @click="clickInterviewQuestionBtn(interviewInfo.interviewId)"
        class="block ml-auto flex flex-col items-center px-7 py-5 btn btn-primary btn-sm"
      >
        질문 관리
      </button>
    </div>
    <div class="table flex flex-col w-full overflow-x-auto sm:rounded-lg">
      <div class="flex bg-gray-50 font-bold text-sm text-gray-800 gap-x-4 px-2">
        <div class="w-[60%] flex flex-col justify-center py-4 text-left">
          면접 제목
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
      </div>
      <div
        class="flex border-b hover:bg-gray-100 gap-x-4 px-2"
        v-for="(interviewQuestion, index) in interviewQuestionList"
        :key="index"
      >
        <div
          class="w-[60%] flex flex-col justify-center py-4 text-left truncate"
        >
          {{ interviewQuestion.questionTitle }}
        </div>
        <div class="w-[15%] flex flex-col justify-center py-4 text-left">
          {{ interviewQuestion.positionName }}
        </div>
        <div class="w-[15%] flex flex-col justify-center py-4 text-left">
          {{ interviewQuestion.questionTypeName }}
        </div>
        <div class="w-[5%] flex flex-col justify-center py-4 text-left">
          {{ interviewQuestion.questionLevelName }}
        </div>
        <div class="w-[5%] flex flex-col justify-center py-4 text-left">
          {{ interviewQuestion.version }}
        </div>
      </div>
    </div>
  </section>
  <UpdateInterviewModal ref="updateInterviewModal" @init="init" />
</template>

<script setup>
import { useRoute } from "vue-router";
import { getInterviewQuestion } from "@/api/interviewQuestion";
import { getInterview } from "@/api/interview";
import { ref } from "vue";
import router from "@/router";

const route = useRoute();
const interviewQuestionList = ref([]);
const interviewInfo = ref();
const updateInterviewModal = ref(null);

const init = async () => {
  const interviewQuestionResponse = await getInterviewQuestion(
    route.params.interviewId,
  );

  console.log(interviewQuestionResponse);
  const interviewInfoResponse = await getInterview(route.params.interviewId);
  console.log(interviewInfoResponse);

  interviewQuestionList.value = interviewQuestionResponse.data;

  interviewInfo.value = interviewInfoResponse.data;
  console.log();
};

init();

const clickUpdateInterviewBtn = () => {
  updateInterviewModal.value.toggleModal(interviewInfo.value);
};

const clickInterviewStartBtn = (interviewId) => {
  router.push(`/interview/start/${interviewId}`);
};
const clickInterviewQuestionBtn = (interviewId) => {
  router.push(`/interview/question/${interviewId}`);
};
</script>
<style scoped></style>