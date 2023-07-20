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

  <section>
    <template
      v-for="(
        item, index
      ) in interviewResultData.responseInterviewQuestionResultDtoList"
    >
      <div v-if="!editModes[index]" class="mb-20">
        <div class="flex justify-between items-end border-b-2 mb-8">
          <span class="mb-2 text-2xl font-semibold text-gray-800"
            >질문{{ index + 1 }}</span
          >
          <div class="flex mb-2">
            <button
              class="flex flex-col mr-3 px-5 py-5 btn btn-outline btn-primary btn-sm"
              @click="clickEditBtn(index)"
            >
              수정
            </button>
            <button
              class="flex flex-col items-center mr-2 px-5 py-5 btn btn-primary btn-sm"
              @click="
                requestChatGptEvaluation(
                  index,
                  item.responseGptCommentDto.gptCommentId,
                )
              "
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
          <div class="bg-gray-100 rounded-lg p-4 whitespace-pre-line">
            <p>{{ item.responseCommentDto.content }}</p>
          </div>
        </div>
        <div class="mb-8">
          <h3 class="text-xl font-semibold mb-4">응답기록</h3>
          <div class="bg-gray-100 rounded-lg p-4 whitespace-pre-line">
            <p>{{ item.responseInterviewAnswerDto.content }}</p>
          </div>
        </div>
        <div class="mb-8">
          <h3 class="text-xl font-semibold mb-4">AI평가</h3>
          <div class="bg-gray-100 rounded-lg p-4 whitespace-pre-line">
            <p>
              {{
                item.responseGptCommentDto.responseContent
                  ? item.responseGptCommentDto.responseContent
                  : `AI 평가가 존재하지 않습니다. ChatGPT를 이용한 AI 평가를 경험해 보세요.`
              }}
            </p>
          </div>
        </div>
      </div>
      <!-- 수정모드 -->
      <div v-else class="mb-20">
        <div class="flex justify-between items-end border-b-2 mb-8">
          <span class="mb-2 text-2xl font-semibold text-gray-800"
            >질문{{ index + 1 }}에 대한 결과 수정</span
          >
          <div class="flex mb-2">
            <button
              class="flex flex-col mr-3 px-5 py-5 btn btn-outline btn-primary btn-sm"
              @click="clickEditBtn(index)"
            >
              취소
            </button>
            <button
              class="flex flex-col items-center mr-2 px-5 py-5 btn btn-primary btn-sm"
              @click="clickUpdateInterviewResultBtn"
            >
              저장
            </button>
          </div>
        </div>
        <h3 class="text-xl text-gray-800 mb-4 font-semibold">
          {{ newInterviewQuestionResult.interviewQuestionTitle }}
        </h3>
        <div class="mb-8">
          <template
            v-for="(
              checklist, index
            ) in newInterviewQuestionResult.responseChecklistDtoList"
            :key="index"
          >
            <div class="flex items-center mb-3 gap-x-2">
              <input
                type="checkbox"
                :checked="newChecklistArray[index]"
                @click="toggleChecked(index)"
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
            v-model="newCommentData.content"
            class="w-full textarea textarea-bordered h-24 text-lg"
          ></textarea>
        </div>
        <div class="mb-8">
          <h3 class="text-xl font-semibold mb-4">응답기록</h3>
          <div class="bg-gray-100 rounded-lg p-4 whitespace-pre-line">
            <p>
              {{
                newInterviewQuestionResult.responseInterviewAnswerDto.content
              }}
            </p>
          </div>
        </div>
        <div class="mb-8">
          <h3 class="text-xl font-semibold mb-4">AI평가</h3>
          <div class="bg-gray-100 rounded-lg p-4 whitespace-pre-line">
            <p>
              {{
                newInterviewQuestionResult.responseGptCommentDto.responseContent
                  ? newInterviewQuestionResult.responseGptCommentDto
                      .responseContent
                  : `AI 평가가 존재하지 않습니다. ChatGPT를 이용한 AI 평가를 경험해 보세요.`
              }}
            </p>
          </div>
        </div>
      </div>
    </template>
  </section>
  <GptLoadingModal ref="gptLoadingModal" />
</template>

<script setup>
import { useRoute } from "vue-router";
import { getInterviewResult } from "@/api/interviewResult";
import { ref } from "vue";
import { updateInterviewQuestionResult } from "@/api/interviewQuestionResult";
import { getGptComment } from "@/api/gptComment";
import GptLoadingModal from "@/components/modal/GptLoadingModal.vue";

const route = useRoute();
const gptLoadingModal = ref(null);
const interviewResultId = route.params.interviewResultId;
const interviewResultData = ref({});
const editModes = ref();
const newInterviewQuestionResult = ref();
const newCommentData = ref();
const newChecklistData = ref();
const newChecklistArray = ref([]);

const init = async () => {
  console.log(interviewResultId);
  const response = await getInterviewResult(interviewResultId);
  console.log(response.data);
  interviewResultData.value = response.data;
  editModes.value = Array(
    interviewResultData.value.responseInterviewQuestionResultDtoList.length,
  ).fill(false);
};
init();

const clickEditBtn = (index) => {
  console.log("clickEditBtn", index);
  if (
    editModes.value.includes(true) &&
    editModes.value.indexOf(true) !== index
  ) {
    alert(
      "이미 수정중인 질문이 존재합니다.\n수정 작업을 완료하고 다른 질문을 수정해주세요.",
    );
    return;
  }
  newInterviewQuestionResult.value =
    interviewResultData.value.responseInterviewQuestionResultDtoList[index];

  newCommentData.value = {
    commentId: newInterviewQuestionResult.value.responseCommentDto.commentId,
    content: newInterviewQuestionResult.value.responseCommentDto.content,
  };
  newChecklistData.value =
    newInterviewQuestionResult.value.responseChecklistDtoList;
  newChecklistArray.value = [];
  newInterviewQuestionResult.value.responseChecklistDtoList.forEach((item) => {
    newChecklistArray.value.push(item.isChecked === "Y" ? true : false);
  });

  editModes.value[index] = !editModes.value[index];
};

const toggleChecked = (index) => {
  newChecklistArray.value[index] = !newChecklistArray.value[index];
};

const clickUpdateInterviewResultBtn = async () => {
  console.log("clickUpdateInterviewResultBtn");

  newChecklistData.value =
    newInterviewQuestionResult.value.responseChecklistDtoList.map(
      (item, index) => {
        return {
          checklistResultId: item.checklistResultId,
          isChecked: newChecklistArray.value[index] ? "Y" : "N",
        };
      },
    );
  const requestData = {
    commentDto: newCommentData.value,
    checklistResultDtoList: newChecklistData.value,
  };
  console.log(requestData);
  await updateInterviewQuestionResult(requestData);
  await init();
};

const requestChatGptEvaluation = async (index, gptCommentId) => {
  const agreeFlag = confirm("ChatGPT에게 답변 평가 요청을 하시겠습니까?");
  if (!agreeFlag) {
    return;
  }
  gptLoadingModal.value.toggleModal();
  const response = await getGptComment(gptCommentId);
  console.log(response.data);
  let currentInterviewResult =
    interviewResultData.value.responseInterviewQuestionResultDtoList[index];
  currentInterviewResult.responseGptCommentDto.responseContent =
    response.data.responseContent;
  gptLoadingModal.value.toggleModal();
};
</script>
