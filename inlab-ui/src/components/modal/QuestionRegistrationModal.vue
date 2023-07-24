<template>
  <dialog id="registerModal" class="modal">
    <form method="dialog" class="modal-box">
      <div class="flex justify-between items-center mb-10">
        <h2 class="font-bold text-xl">질문 제목</h2>
        <label class="btn btn-sm btn-circle btn-ghost" @click="toggleModal"
          >✕</label
        >
      </div>
      <div class="mb-5">
        <label
          for="questionTitle"
          class="block mb-1.5 text-sm font-semibold text-gray-700"
          >질문 제목</label
        >
        <input
          type="text"
          v-model="registerData.title"
          class="w-11/12 input input-sm input-bordered border-gray-300 text-sm"
          placeholder="질문을 입력해주세요"
          required
          autofocus
        />
      </div>

      <div class="mb-5">
        <label
          for="questionType"
          class="block mb-1.5 text-sm font-semibold text-gray-700"
          >직무</label
        >
        <select
          class="w-full font-medium select select-primary select-sm border-gray-300 max-w-xs"
          v-model="registerData.positionId"
          @change="changePosition"
        >
          <option disabled value="">직무 선택</option>
          <option
            v-for="(option, index) in positionOptions"
            :key="index"
            :value="option.positionId"
          >
            {{ option.positionName }}
          </option>
        </select>
      </div>
      <div class="w-full flex justify-between mb-5">
        <div class="w-[45%]">
          <label
            for="job"
            class="block mb-1.5 text-sm font-semibold text-gray-700"
            >유형</label
          >
          <select
            class="w-full select select-primary select-sm border-gray-300 font-medium max-w-xs"
            v-model="registerData.questionTypeId"
            :disabled="!registerData.positionId"
          >
            <option selected disabled value="">유형 선택</option>
            <option
              v-for="(type, index) in typeOptions"
              :key="index"
              :value="type.questionTypeId"
            >
              {{ type.questionTypeName }}
            </option>
          </select>
        </div>

        <div class="w-[25%]">
          <label
            for="questionLevel"
            class="block mb-1.5 text-sm font-semibold text-gray-700"
            >난이도</label
          >
          <select
            class="w-full font-medium select select-primary select-sm border-gray-300 max-w-xs"
            v-model="registerData.questionLevelId"
            :disabled="!registerData.positionId"
          >
            <option disabled value="">난이도</option>
            <option
              v-for="(option, index) in levelOptions"
              :key="index"
              :value="option.levelId"
            >
              {{ option.levelName }}
            </option>
          </select>
        </div>
        <div class="w-[25%]">
          <label
            for="questionVersion"
            class="block mb-1.5 text-sm font-semibold text-gray-700"
            >버전</label
          >
          <input
            type="text"
            class="w-full p-2.5 input input-sm input-bordered border-gray-300 text-sm"
            placeholder="1"
            disabled
          />
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
            class="w-[90%] p-2.5 input input-sm input-primary input-bordered border-gray-300 text-sm"
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
          @click="resetFields"
          type="submit"
          class="btn btn-outline btn-primary btn-md w-full mt-2"
        >
          초기화
        </button>
        <button
          @click="registerBtn"
          type="button"
          class="btn btn-primary btn-md w-full mt-2"
        >
          등록
        </button>
      </div>
    </form>
  </dialog>
</template>
<script setup>
import { defineEmits, ref } from "vue";
import { createQuestion } from "@/api/question";
import { getpositionOption, getTypeOption } from "@/api/select";

const emit = defineEmits(["init"]);
const checkList = ref([]);

//selectOptions
const positionOptions = ref([]);
const levelOptions = ref([]);
const typeOptions = ref([]);

const initialRegisterData = {
  checklists: [],
  positionId: "",
  questionLevelId: "",
  questionTypeId: "",
  title: "",
  version: 1,
};

const registerData = ref({ ...initialRegisterData });

const init = async () => {
  const positionOptionInfo = await getpositionOption();
  positionOptions.value = positionOptionInfo.data;
};

init();

const changePosition = async () => {
  const selectedOption = registerData.value.positionId;
  levelOptions.value = positionOptions?.value.find(
    (option) => option.positionId === selectedOption,
  )?.levelListDto;
  const requestData = {
    positionId: selectedOption,
  };
  const typeOptionInfo = await getTypeOption(requestData);
  typeOptions.value = typeOptionInfo.data;
  console.log(typeOptions.value);
};

const resetFields = () => {
  registerData.value = { ...initialRegisterData };
  checkList.value = [];
};

const makeCheckList = () => {
  checkList.value.push("");
};
const removeCheckList = (index) => {
  checkList.value.splice(index, 1);
};

const registerBtn = async () => {
  registerData.value.checklists = checkList.value.filter(
    (data) => data.trim() !== "",
  );

  if (!registerData.value.title) {
    alert("질문 제목을 입력하세요.");
    return;
  }
  if (!registerData.value.positionId) {
    alert("직무를 선택하세요");
    return;
  }
  if (!registerData.value.questionTypeId) {
    alert("질문의 유형을 선택하세요");
    return;
  }
  if (!registerData.value.questionLevelId) {
    alert("질문의 난이도를 선책하세요.");
    return;
  }

  await createQuestion(registerData.value);
  alert("등록이 완료되었습니다.");
  emit("init");
  toggleModal();
};

const toggleModal = () => {
  resetFields();
  document.getElementById("registerModal").classList.toggle("modal-open");
};

defineExpose({
  toggleModal,
});
</script>
<style scoped></style>