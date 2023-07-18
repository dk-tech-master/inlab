<template>
  <header>
    <div>
      <div class="mb-10">
        <p class="mb-1 text-sm font-light text-gray-500">
          면접 관리 >
          <span class="font-medium text-indigo-500">면접 결과 관리</span>
        </p>
        <h2 class="text-3xl tracking-tight font-bold text-gray-800">
          면접 결과 관리
        </h2>
      </div>
      <div class="mb-10 p-8 shadow rounded-lg border border-gray-200">
        <h4 class="text-base mb-1 tracking-tight">
          {{ interviewResultData.interviewTitle }}
        </h4>
        <h2 class="text-2xl font-medium">
          <span class="text-primary font-bold">{{
            interviewResultData.intervieweeName
          }}</span
          >님의 면접 결과
        </h2>
      </div>
    </div>
  </header>

  <section v-if="!editMode">
    <template
      v-for="(
        item, index
      ) in interviewResultData.responseInterviewQuestionResultDtoList"
    >
      <div class="mb-20">
        <div class="flex justify-between items-end border-b-2 mb-8">
          <span class="mb-2 text-2xl font-semibold text-gray-800"
            >질문{{ index + 1 }}</span
          >
          <div class="flex mb-2">
            <button
              class="flex flex-col mr-3 px-7 py-5 btn btn-outline btn-primary btn-sm"
            >
              수정
            </button>
            <button
              class="flex flex-col items-center mr-2 px-7 py-5 btn btn-primary btn-sm"
            >
              AI 평가 측정
            </button>
          </div>
        </div>
        <h3 class="text-xl text-gray-800 mb-4 font-semibold">
          {{ item.interviewQuestionTitle }}
        </h3>
        <div class="mb-8">
          <template
            v-for="(checklist, index) in item.responseChecklistDtoList"
            :key="index"
          >
            <div class="flex items-center mb-3 gap-x-2">
              <div v-if="checklist.isChecked === `Y`">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke-width="2"
                  stroke="currentColor"
                  class="w-6 h-6 text-success"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    d="M9 12.75L11.25 15 15 9.75M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                  />
                </svg>
              </div>
              <div v-else>
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke-width="2"
                  stroke="currentColor"
                  class="w-6 h-6 text-error"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    d="M15 12H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z"
                  />
                </svg>
              </div>
              <p class="font-medium text-gray-900">
                {{ checklist.content }}
              </p>
            </div>
          </template>
        </div>
        <div class="mb-8">
          <h3 class="text-xl font-semibold mb-4">코멘트</h3>
          <div class="bg-gray-100 rounded-lg p-4">
            <p>{{ item.responseCommentDto.content }}</p>
          </div>
        </div>
        <div class="mb-8">
          <h3 class="text-xl font-semibold mb-4">응답기록</h3>
          <div class="bg-gray-100 rounded-lg p-4">
            <p>{{ item.responseInterviewAnswerDto.content }}</p>
          </div>
        </div>
        <div class="mb-8">
          <h3 class="text-xl font-semibold mb-4">AI평가</h3>
          <div class="bg-gray-100 rounded-lg p-4">
            <p>
              {{
                item.responseGptCommentDto.responseContent
                  ? iterm.responseGptCommentDto.responseContent
                  : `AI 평가가 존재하지 않습니다. ChatGPT를 이용한 AI 평가를 경험해 보세요.`
              }}
            </p>
          </div>
        </div>
      </div>
    </template>
  </section>
  <section v-else>
    <template
      v-for="(
        item, index
      ) in interviewResultData.responseInterviewQuestionResultDtoList"
    >
      <div class="mb-20">
        <div class="flex justify-between items-end border-b-2 mb-8">
          <span class="mb-2 text-2xl font-semibold text-gray-800"
            >질문{{ index + 1 }}</span
          >
          <div class="flex mb-2">
            <button
              class="flex flex-col mr-3 px-7 py-5 btn btn-outline btn-primary btn-sm"
            >
              수정
            </button>
            <button
              class="flex flex-col items-center mr-2 px-7 py-5 btn btn-primary btn-sm"
            >
              AI 평가 측정
            </button>
          </div>
        </div>
        <h3 class="text-xl text-gray-800 mb-4 font-semibold">
          {{ item.interviewQuestionTitle }}
        </h3>
        <div class="mb-8">
          <template
            v-for="(checklist, index) in item.responseChecklistDtoList"
            :key="index"
          >
            <div class="flex items-center mb-3 gap-x-2">
              <input
                type="checkbox"
                :checked="checklist.isChecked === `Y` ? true : false"
                class="rounded focus:ring-2 focus:ring-primary checkbox checkbox-sm checkbox-primary"
              />
              <p class="font-medium text-gray-900">
                {{ checklist.content }}
              </p>
            </div>
          </template>
        </div>
        <div class="mb-8">
          <h3 class="text-xl font-semibold mb-4">코멘트</h3>
          <textarea
            class="w-full bg-gray-100 textarea textarea-bordered h-24"
            placeholder="면접관님의 의견을 적어주세요"
          ></textarea>
        </div>
        <div class="mb-8">
          <h3 class="text-xl font-semibold mb-4">응답기록</h3>
          <textarea
            class="w-full bg-gray-100 textarea textarea-bordered h-24"
          ></textarea>
        </div>
        <div class="mb-8">
          <h3 class="text-xl font-semibold mb-4">AI평가</h3>
          <textarea
            class="w-full bg-gray-100 textarea textarea-bordered h-24"
          ></textarea>
        </div>
      </div>
    </template>
  </section>
</template>

<script setup>
import { useRoute } from "vue-router";
import { getInterviewResult } from "@/api/interviewResult";
import { ref } from "vue";

const route = useRoute();
const interviewResultId = route.params.interviewResultId;
const interviewResultData = ref({});
const editMode = ref(false);

const init = async () => {
  console.log(interviewResultId);
  const response = await getInterviewResult(interviewResultId);
  console.log(response.data);
  interviewResultData.value = response.data;
};
init();
</script>
