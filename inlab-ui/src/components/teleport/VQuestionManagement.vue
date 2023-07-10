<template>
  <input type="checkbox" id="my_modal_7" class="modal-toggle" />
  <div class="modal">
    <div class="modal-box pt-10 px-10">
      <slot name="header"></slot>
      <slot name="body">
        <div class="mb-5">
          <label
            for="questionTitle"
            class="block mb-1.5 text-sm font-semibold text-gray-700"
            >질문 제목</label
          >
          <input
            type="text"
            name="nickName"
            class="w-full input input-sm input-bordered border-gray-300 text-sm"
            placeholder="질문을 입력해주세요"
            required
          />
        </div>
        <div class="mb-5">
          <label
            for="questionType"
            class="block mb-1.5 text-sm font-semibold text-gray-700"
            >질문 유형</label
          >
          <select
            class="w-full font-medium select select-primary select-sm border-gray-300 max-w-xs"
          >
            <option value="유형1">기술적인 지식 및 개념 질문</option>
            <option value="유형2">기술적인 지식 및 개념 질문</option>
            <option value="유형3">기술적인 지식 및 개념 질문</option>
            <option value="유형4">기술적인 지식 및 개념 질문</option>
            <option value="유형5">기술적인 지식 및 개념 질문</option>
          </select>
        </div>
        <div class="w-full flex justify-between mb-5">
          <div class="w-1/3">
            <label
              for="job"
              class="block mb-1.5 text-sm font-semibold text-gray-700"
              >직무</label
            >
            <select
              class="w-full select select-primary select-sm font-medium border-gray-300 max-w-xs"
            >
              <option value="프론트앤드">프론트엔드</option>
              <option value="백엔드">백엔드</option>
            </select>
          </div>
          <div class="w-[30%]">
            <label
              for="questionLevel"
              class="block mb-1.5 text-sm font-semibold text-gray-700"
              >난이도</label
            >
            <select
              class="w-full font-medium select select-primary select-sm border-gray-300 max-w-xs"
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
            <input
              type="text"
              name="nickName"
              class="w-full p-2.5 input input-sm input-bordered border-gray-300 text-sm"
              placeholder="1"
              required
            />
          </div>
        </div>
        <div class="mb-5 checkInput">
          <label
            for="questionVersion"
            class="block mb-1.5 text-sm font-semibold text-gray-700"
            >체크리스트</label
          >
          <!--          <div v-if="checkList === 0">-->
          <!--            <p>체크할 내용이 없어요</p>-->
          <!--          </div>-->
          <div
            class="flex justify-between mb-3"
            v-for="(check, index) in checkList"
            :key="index"
          >
            <input
              type="text"
              name="nickName"
              class="w-[92%] p-2.5 input input-sm input-bordered border-gray-300 text-sm"
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
            @click="addCheckList"
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
      </slot>
      <slot name="footer">
        <button
          type="submit"
          class="btn btn-outline btn-primary btn-md w-full mt-2"
        >
          초기화
        </button>
        <button type="submit" class="btn btn-primary btn-md w-full mt-2">
          등록
        </button>
      </slot>
    </div>
    <label class="modal-backdrop" for="my_modal_7">Close</label>
  </div>
</template>
<script setup>
import { ref } from "vue";

const checkList = ref([]);
const addCheckList = () => {
  checkList.value.push("");
};

const removeCheckList = (index) => {
  checkList.value.splice(index, 1);
};
</script>
<style scoped></style>
