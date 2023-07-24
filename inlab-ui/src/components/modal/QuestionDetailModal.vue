<template>
  <input type="checkbox" id="detailViewModal" class="modal-toggle" />
  <div class="modal">
    <div class="modal-box">
      <div class="flex justify-between items-center mb-8">
        <h2 class="font-bold text-xl">질문 상세</h2>
        <label class="btn btn-sm btn-circle btn-ghost" for="detailViewModal"
          >✕</label
        >
      </div>

      <div class="mb-4">
        <label for="questionTitle" class="block mb-2 font-semibold"
          >질문 제목</label
        >
        <div class="flex items-center rounded-lg p-3 bg-gray-100">
          <p class="input input-sm text-sm pl-0 text-gray-700 bg-gray-100">
            {{ title }}
          </p>
        </div>
      </div>

      <div class="mb-4">
        <label for="questionType" class="block mb-2 font-semibold"
          >질문 유형</label
        >
        <div class="flex items-center rounded-lg p-3 bg-gray-100">
          <p class="input input-sm text-sm pl-0 text-gray-700 bg-gray-100">
            {{ type }}
          </p>
        </div>
      </div>

      <div class="flex justify-between mb-4">
        <div class="w-[30%]">
          <label for="job" class="block mb-2 font-semibold">직무</label>
          <div class="flex items-center rounded-lg p-3 bg-gray-100">
            <p class="input input-sm text-sm pl-0 text-gray-700 bg-gray-100">
              {{ position }}
            </p>
          </div>
        </div>

        <div class="w-[30%]">
          <label for="questionLevel" class="block mb-2 font-semibold"
            >난이도</label
          >
          <div class="flex items-center rounded-lg p-3 bg-gray-100">
            <p class="input input-sm text-sm pl-0 text-gray-700 bg-gray-100">
              {{ level }}
            </p>
          </div>
        </div>
        <div class="w-[30%]">
          <label for="questionVersion" class="block mb-2 font-semibold"
            >버전</label
          >
          <div class="flex items-center rounded-lg p-3 bg-gray-100">
            <p
              class="w-full input input-sm text-sm pl-0 text-gray-700 bg-gray-100"
            >
              {{ version }}
            </p>
          </div>
        </div>
      </div>
      <div class="mb-8 checkInput">
        <label for="questionVersion" class="block mb-2 font-semibold"
          >체크리스트</label
        >
        <div class="rounded-lg p-3 pt-3 bg-gray-100">
          <div
            v-for="(check, index) in checkList"
            :key="index"
            class="flex gap-x-1.5 text-sm pl-0 text-gray-700 mb-3"
          >
            <span>{{ index + 1 }}.</span>
            <span>{{ check }}</span>
          </div>
        </div>
      </div>
      <div>
        <label
          for="detailViewModal"
          @click="resetFields"
          class="btn btn-outline btn-primary w-full"
        >
          확인
        </label>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref } from "vue";
import { getQuestionDetail } from "@/api/question";

const username = ref("");
const title = ref("");
const type = ref("");
const position = ref("");
const level = ref("");
const checkList = ref([]);
const questionId = ref(null);
const version = ref("");

const init = async (id) => {
  username.value = sessionStorage.getItem("email");
  questionId.value = id;
  const response = await getQuestionDetail(id, username.value);
  console.log(response.data);
  title.value = response.data.title;
  position.value = response.data.positionName;
  type.value = response.data.questionTypeName;
  level.value = response.data.questionLevelId;
  checkList.value = response.data.checklists;
  version.value = response.data.version;
  console.log(response.data.checklists);
  console.log(checkList.value);
};
const toggleModal = async (id) => {
  document.getElementById("detailViewModal").classList.toggle("modal-open");
  await init(id);
};

defineExpose({
  toggleModal,
});
</script>
<style scoped></style>
