<template>
  <input type="checkbox" id="detailViewModal" class="modal-toggle" />
  <div class="modal">
    <div class="modal-box pt-10 px-10">
      <div class="font-semibold text-2xl text-gray-800 mb-10">
        <label
          class="cursor-pointer modal-backdrop text-gray-700 flex items-end justify-end"
          for="detailViewModal"
          >x</label
        >
        질문상세
      </div>

      <div class="mb-5">
        <label
          for="questionTitle"
          class="block mb-1.5 text-sm font-semibold text-gray-700"
          >질문 제목</label
        >
        <p class="w-full input input-sm text-sm pl-0 text-primary">
          {{ title }}
        </p>
      </div>

      <div class="mb-5">
        <label
          for="questionType"
          class="block mb-1.5 text-sm font-semibold text-gray-700"
          >질문 유형</label
        >
        <p class="w-full input input-sm text-sm pl-0 text-primary">
          {{ type }}
        </p>
      </div>

      <div class="w-full flex justify-between mb-5">
        <div class="w-[30%]">
          <label
            for="job"
            class="block mb-1.5 text-sm font-semibold text-gray-700"
            >직무</label
          >
          <p class="w-full input input-sm text-sm pl-0 text-primary">
            {{ position }}
          </p>
        </div>

        <div class="w-[30%]">
          <label
            for="questionLevel"
            class="block mb-1.5 text-sm font-semibold text-gray-700"
            >난이도</label
          >
          <p class="w-full input input-sm text-sm pl-0 text-primary">
            {{ level }}
          </p>
        </div>
        <div class="w-[30%]">
          <label
            for="questionVersion"
            class="block mb-1.5 text-sm font-semibold text-gray-700"
            >버전</label
          >
          <p class="w-full input input-sm text-sm pl-0 text-primary">
            {{ version }}
          </p>
        </div>
      </div>
      <div class="mb-5 checkInput">
        <label
          for="questionVersion"
          class="block mb-1.5 text-sm font-semibold text-gray-700"
          >체크리스트</label
        >
        <p
          v-for="(check, index) in checkList"
          :key="index"
          class="w-full input input-sm text-sm pl-0 text-primary"
        >
          {{ index + 1 }}.{{ check }}
        </p>
      </div>
      <div class="mb-10">
        <label
          for="detailViewModal"
          @click="resetFields"
          class="btn btn-outline btn-primary btn-md w-full mt-2"
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
