<template>
  <header class="w-3/4 mx-auto mb-10">
    <div>
      <h4 class="text-3xl font-semibold mb-5">{{ interviewTitle }}</h4>
    </div>
  </header>
  <section class="w-3/4 mx-auto">
    <template v-for="(item, index) in interviewQuestionList" :key="index">
      <div class="mb-20">
        <div class="flex justify-between items-end border-b-2 mb-8 pb-2">
          <span class="mb-2 text-2xl font-semibold text-gray-800"
            >질문{{ index + 1 }}</span
          >
          <div class="flex items-center">
            <span
              v-if="recordingModeList[index]"
              class="relative flex h-4 w-4 mr-4"
            >
              <span
                class="animate-ping absolute inline-flex h-full w-full rounded-full bg-red-400 opacity-85"
              ></span>
              <span
                class="relative inline-flex rounded-full h-4 w-4 bg-red-500"
              ></span>
            </span>
            <div>
              <button
                :class="recordingModeList[index] ? 'btn-error' : 'btn-neutral'"
                class="btn btn-sm py-5 px-5 flex flex-col items-center self-end"
                @click="clickRecordBtn(index)"
              >
                {{ recordingModeList[index] ? "녹음중" : "녹음 시작" }}
              </button>
            </div>
          </div>
        </div>
        <h3 class="text-xl text-gray-800 mb-4 font-semibold">
          {{ item.questionTitle }}
        </h3>
        <div class="mb-8">
          <template
            v-for="(item, checklistIndex) in item.checklistList"
            :key="checklistIndex"
          >
            <div class="flex items-center mb-3 gap-x-2">
              <input
                type="checkbox"
                :checked="checklistArray[index][checklistIndex]"
                @click="toggleChecked(index, checklistIndex)"
                class="rounded focus:ring-2 focus:ring-primary checkbox checkbox-sm checkbox-primary"
              />
              <p for="default-checkbox" class="font-medium text-gray-900">
                {{ item.content }}
              </p>
            </div>
          </template>
        </div>
        <div class="mb-8">
          <h3 class="text-xl font-semibold mb-4">코멘트</h3>
          <textarea
            v-model="commentList[index]"
            class="w-full bg-gray-100 textarea textarea-bordered h-24 text-lg"
            placeholder="면접관님의 의견을 적어주세요"
          ></textarea>
        </div>
        <div class="my-8">
          <h3 class="text-xl font-semibold mb-4">응답기록</h3>
          <div class="bg-gray-100 rounded-lg p-4 whitespace-pre-line">
            <p
              v-text="
                answerList[index]
                  ? answerList[index]
                  : `질문 우측 상단에 답변 녹음 기능을 사용하시면 면접자의 음성 답변을 텍스트 데이터로 변환하여 제공받을 수 있습니다.\n답변 녹음 기능을 사용해보세요😀`
              "
            ></p>
          </div>
        </div>
      </div>
    </template>
  </section>

  <!--  footer고정-->
  <div
    class="fixed bottom-0 left-0 z-50 w-full bg-white border-t border-gray-200"
  >
    <div class="flex justify-center h-full max-w-4xl mx-auto font-medium">
      <div class="self-center">
        <div class="flex justify-center items-center gap-x-2 p-6">
          <span class="text-lg text-gray-500">면접자</span>
          <span class="text-primary text-2xl font-semibold">{{
            route.query.interviewee
          }}</span>
        </div>
      </div>
      <div
        class="group flex justify-center self-center cursor-pointer"
        @click="clickCancelBtn"
      >
        <div class="transition group-hover:bg-gray-200 p-6">
          <div class="flex justify-center mb-2">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor"
              class="w-8 h-8 text-gray-500 transition group-hover:text-primary"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M9.75 9.75l4.5 4.5m0-4.5l-4.5 4.5M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
              />
            </svg>
          </div>
          <div class="flex justify-center">
            <span class="text-gray-500 transition group-hover:text-primary"
              >면접 취소</span
            >
          </div>
        </div>
      </div>
      <div class="group flex justify-center self-center cursor-pointer">
        <div
          class="transition group-hover:bg-gray-200 p-6"
          @click="clickInterviewSaveBtn"
        >
          <div class="flex justify-center mb-2">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke-width="1.5"
              stroke="currentColor"
              class="w-8 h-8 text-gray-500 transition group-hover:text-primary"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M9 12.75l3 3m0 0l3-3m-3 3v-7.5M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
              />
            </svg>
          </div>
          <div class="flex justify-center">
            <span class="text-gray-500 transition group-hover:text-primary"
              >면접 종료 및 저장</span
            >
          </div>
        </div>
      </div>
    </div>
  </div>
  <ClovaLoadingModal ref="clovaLoadingModal" />
</template>
<script setup>
import { ref } from "vue";
import { getInterviewQuestionList } from "@/api/interview";
import { useRoute, useRouter } from "vue-router";
import { uploadAudioFile } from "@/api/file";
import { speechToText } from "@/api/clova";
import ClovaLoadingModal from "@/components/modal/ClovaLoadingModal.vue";
import { createInterviewResult } from "@/api/interviewResult";

const router = useRouter();
const route = useRoute();
const interviewQuestionList = ref();
const interviewTitle = ref();
const commentList = ref();
const answerList = ref();
const checklistArray = ref([]);
const recordingModeList = ref([]);
const mediaRecorder = ref(null);
const chunks = ref([]);
const audioFileUrl = ref("");
const clovaLoadingModal = ref(null);
const currentIndex = ref();

const init = async () => {
  const response = await getInterviewQuestionList(route.params.interviewId);
  interviewQuestionList.value = response.data;
  interviewTitle.value = interviewQuestionList.value[0].interviewTitle;
  commentList.value = Array(interviewQuestionList.value.length).fill("");
  answerList.value = Array(interviewQuestionList.value.length).fill("");
  interviewQuestionList.value.forEach((item) => {
    checklistArray.value.push(Array(item.checklistList.length).fill(false));
  });
  recordingModeList.value = Array(interviewQuestionList.value.length).fill(
    false,
  );
  console.log(interviewTitle.value);
  console.log(interviewQuestionList.value);
  console.log(checklistArray.value);
};
init();

const clickRecordBtn = (index) => {
  recordingModeList.value[index] = !recordingModeList.value[index];
  currentIndex.value = index;
  if (recordingModeList.value[index]) {
    startRecording();
  } else {
    stopRecording();
  }
};

const startRecording = () => {
  chunks.value = [];

  navigator.mediaDevices
    .getUserMedia({ audio: true })
    .then((stream) => {
      mediaRecorder.value = new MediaRecorder(stream);

      mediaRecorder.value.addEventListener("dataavailable", (event) => {
        chunks.value.push(event.data);
      });

      mediaRecorder.value.addEventListener("stop", () => {
        const blob = new Blob(chunks.value, { type: "audio/mp3" });
        audioToText(blob);
      });

      mediaRecorder.value.start();
    })
    .catch((error) => {
      console.error(error);
    });
};

const stopRecording = () => {
  mediaRecorder.value.stop();
};

const audioToText = async (blob) => {
  clovaLoadingModal.value.toggleModal();
  const formData = new FormData();
  formData.append("blob", blob);

  const uploadAudioFileResult = await uploadAudioFile(formData);
  audioFileUrl.value = uploadAudioFileResult.data.audioFileUrl;

  const requestData = {
    url: audioFileUrl.value,
    language: "ko-KR",
    completion: "sync",
  };
  const speechToTextResult = await speechToText(requestData);
  console.log(speechToTextResult.data);
  clovaLoadingModal.value.toggleModal();
  if (speechToTextResult.status === 200) {
    alert("텍스트 변환이 완료되었습니다.");
    answerList.value[currentIndex.value] = speechToTextResult.data.text;
  } else {
    alert("텍스트 변환을 실패했습니다.");
  }
};

const toggleChecked = (index, checklistIndex) => {
  checklistArray.value[index][checklistIndex] =
    !checklistArray.value[index][checklistIndex];
};

const clickInterviewSaveBtn = async () => {
  console.log("clickInterviewSaveBtn");
  console.log(interviewQuestionList.value);
  const interviewQuestionResult = interviewQuestionList.value.map(
    (item, index) => {
      const checklistResult = item.checklistList.map(
        (checklist, checklistIndex) => {
          console.log(index, checklistIndex);
          return {
            checklistId: checklist.checklistId,
            isChecked: checklistArray.value[index][checklistIndex] ? "Y" : "N",
          };
        },
      );
      return {
        interviewQuestionId: item.interviewQuestionId,
        commentDto: {
          content: commentList.value[index],
        },
        interviewAnswerDto: {
          content: answerList.value[index],
        },
        checklistResultDtoList: checklistResult,
      };
    },
  );
  const requestData = {
    intervieweeName: route.query.interviewee,
    interviewId: route.params.interviewId,
    interviewQuestionResultDtoList: interviewQuestionResult,
  };
  console.log(requestData);
  await createInterviewResult(requestData);
  alert("면접 결과 데이터 저장이 완료됐습니다.\n수고하셨습니다.");
  await router.push(`/interview`);
};

const clickCancelBtn = () => {
  router.push(`/interview`);
};
</script>