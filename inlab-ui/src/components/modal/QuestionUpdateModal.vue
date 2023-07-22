<template>
  <dialog id="updateModal" class="modal">
    <form method="dialog" class="modal-box">
      <div class="flex justify-between items-center mb-8">
        <h2 class="font-bold text-xl">질문 수정</h2>
        <label class="btn btn-sm btn-circle btn-ghost" @click="toggleModal"
          >✕</label
        >
      </div>
      <div class="mb-4">
        <label for="questionTitle" class="block mb-2 font-semibold"
          >질문 제목</label
        >
        <input
          type="text"
          v-model="title"
          class="w-full input input-primary input-sm input-bordered border-gray-300 text-sm"
          placeholder="질문을 입력해주세요"
          required
          autofocus
        />
      </div>
      <div class="mb-4">
        <label for="questionType" class="block mb-2 font-semibold"
          >질문 유형</label
        >
        <p class="w-full input input-sm text-sm pl-0 text-gray-700">
          {{ type }}
        </p>
      </div>

      <div class="w-full flex justify-between mb-4">
        <div class="w-[30%]">
          <label for="job" class="block mb-2 font-semibold">직무</label>
          <p class="w-full input input-sm text-sm pl-0 text-gray-700">
            {{ position }}
          </p>
        </div>

        <div class="w-[30%]">
          <label for="questionLevel" class="block mb-2 font-semibold"
            >난이도</label
          >
          <select
            class="w-full font-medium select select-primary select-sm border-gray-300 max-w-xs"
            v-model="level"
          >
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
          </select>
        </div>
        <div class="w-[30%]">
          <label for="questionVersion" class="block mb-2 font-semibold"
            >버전</label
          >
          <p class="w-full input input-sm text-sm pl-0 text-gray-700">
            {{ version }}
          </p>
        </div>
      </div>
      <div class="mb-4 checkInput">
        <label for="questionVersion" class="block mb-2 font-semibold"
          >체크리스트</label
        >
        <div
          class="flex justify-between mb-3"
          v-for="(check, index) in newCheckList"
          :key="index"
        >
          <input
            type="text"
            v-model="newCheckList[index]"
            class="w-[90%] input input-sm input-primary input-bordered border-gray-300 text-sm"
            placeholder="체크내용을 입력해주세요"
            required
          />

          <button type="button" @click="removeCheckList(index)">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="currentColor"
              class="w-6 h-6"
            >
              <path
                fill-rule="evenodd"
                d="M5.25 12a.75.75 0 01.75-.75h12a.75.75 0 010 1.5H6a.75.75 0 01-.75-.75z"
                clip-rule="evenodd"
              />
            </svg>
          </button>
        </div>
        <button
          class="addCheckbtn flex items-center leading-5 mt-2"
          @click="makeCheckList()"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 24 24"
            fill="currentColor"
            class="w-6 h-6"
          >
            <path
              fill-rule="evenodd"
              d="M12 5.25a.75.75 0 01.75.75v5.25H18a.75.75 0 010 1.5h-5.25V18a.75.75 0 01-1.5 0v-5.25H6a.75.75 0 010-1.5h5.25V6a.75.75 0 01.75-.75z"
              clip-rule="evenodd"
            />
          </svg>
          <p class="block ml-2 text-sm font-semibold text-primary">
            체크리스트 추가
          </p>
        </button>
      </div>
      <div>
        <button
          @click="init(questionId)"
          type="submit"
          class="btn btn-outline btn-primary btn-md w-full mb-4"
        >
          초기화
        </button>
        <button
          @click="updateBtn"
          type="button"
          class="btn btn-primary btn-md w-full"
        >
          수정
        </button>
      </div>
    </form>
  </dialog>
</template>
<script setup>
import { defineEmits, ref } from "vue";
import { getQuestionDetail, updateQuestion } from "@/api/question";

const emit = defineEmits(["init"]);

const username = ref("");
const title = ref("");
const type = ref("");
const position = ref("");
const levelId = ref("");
const newCheckList = ref([]);
const oldCheckList = ref([]);
const version = ref("");
const questionId = ref();

const init = async (id) => {
  username.value = sessionStorage.getItem("email");
  questionId.value = id;
  console.log(questionId.value);
  const response = await getQuestionDetail(questionId.value, username.value);
  title.value = response.data.title;
  position.value = response.data.positionName;
  type.value = response.data.questionTypeName;
  levelId.value = response.data.questionLevelId;
  oldCheckList.value = response.data.checklists;
  newCheckList.value = oldCheckList.value;
  version.value = response.data.version;
};

const makeCheckList = () => {
  newCheckList?.value.push("");
};

const removeCheckList = (index) => {
  newCheckList.value.splice(index, 1);
};

const updateBtn = async () => {
  const resultCheckList = newCheckList.value.filter(
    (data) => data.trim() !== "",
  );
  console.log(resultCheckList);
  const updateData = {
    questionLevelId: levelId.value,
    title: title.value,
    checklists: resultCheckList,
  };
  console.log(updateData);

  const response = await updateQuestion(updateData, questionId.value);
  console.log(response);
  window.alert("수정이 완료되었습니다.");
  toggleModal();
  emit("init");
};

const openModal = (id) => {
  init(id);
  toggleModal();
};

const toggleModal = async () => {
  document.getElementById("updateModal").classList.toggle("modal-open");
};

defineExpose({
  openModal,
});
</script>
<style scoped></style>