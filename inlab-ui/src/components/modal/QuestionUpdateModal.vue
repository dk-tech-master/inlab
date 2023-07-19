<template>
  <input type="checkbox" id="updateModal" class="modal-toggle" />
  <div class="modal">
    <div class="modal-box pt-10 px-10">
      <div class="font-semibold text-2xl text-gray-800 mb-10">
        <label
          class="cursor-pointer modal-backdrop text-gray-700 flex items-end justify-end"
          for="updateModal"
          >x</label
        >
        질문수정
      </div>

      <div class="mb-5">
        <label
          for="questionTitle"
          class="block mb-1.5 text-sm font-semibold text-gray-700"
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
        <div
          class="flex justify-between mb-3"
          v-for="(check, index) in checkList"
          :key="index"
        >
          <input
            type="text"
            v-model="checkList[index]"
            class="w-[85%] p-2.5 input input-sm input-primary input-bordered border-gray-300 text-sm"
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
          <button type="button" @click="addCheckList(inputCheck[index])">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="black"
              class="w-6 h-6"
            >
              <path
                fill-rule="evenodd"
                d="M12 5.25a.75.75 0 01.75.75v5.25H18a.75.75 0 010 1.5h-5.25V18a.75.75 0 01-1.5 0v-5.25H6a.75.75 0 010-1.5h5.25V6a.75.75 0 01.75-.75z"
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
      <div class="mb-10">
        <button
          @click="resetFields"
          type="submit"
          class="btn btn-outline btn-primary btn-md w-full mt-2"
        >
          초기화
        </button>
        <button
          @click="updateBtn"
          type="button"
          class="btn btn-primary btn-md w-full mt-2"
        >
          수정
        </button>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref } from "vue";
import { getQuestionDetail, updateQuestion } from "@/api/question";

const username = ref("");
const questionId = ref(null);

const title = ref("");
const type = ref("");
const position = ref("");
const level = ref("");
const checkList = ref([]);
const inputCheck = ref([]);
const version = ref("");

const resetFields = () => {
  title.value = "";
  level.value = "";
  checkList.value = [];
  inputCheck.value = [];
};

const makeCheckList = () => {
  console.log("1: ", checkList.value.length);
  checkList.value.push("");
  console.log("2: ", checkList.value.length);
};

const addCheckList = (check) => {
  if (
    checkList.value.length > 0 &&
    checkList.value[checkList.value.length - 1] === ""
  ) {
    console.log("3: ", checkList.value.length);
    checkList.value[checkList.value.length - 1] = check;
    console.log(check);
    console.log("4: ", checkList.value.length);
  }
};

const removeCheckList = (index) => {
  checkList.value.splice(index, 1);
};

const updateBtn = async () => {
  if (
    checkList.value.length > 0 &&
    checkList.value[checkList.value.length - 1] === ""
  ) {
    checkList.value.pop();
  }
  const updateData = {
    checklists: checkList.value,
    questionLevelId: level.value,
    title: title.value,
  };
  try {
    const response = await updateQuestion(updateData, questionId);
    alert("수정이 완료되었습니다.");
  } catch (error) {
    console.error(error);
  }
};

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
  document.getElementById("updateModal").classList.toggle("modal-open");
  await init(id);
};

defineExpose({
  toggleModal,
});
</script>
<style scoped></style>
